package com.server.map.service;

import com.SpringContext;
import com.server.common.command.SceneRateCommand;
import com.server.common.resource.ResourceManager;
import com.server.map.command.ChangeMapCommand;
import com.server.map.model.Grid;
import com.server.map.model.MapInfo;
import com.server.map.packet.SM_AccountMove;
import com.server.map.packet.SM_ChangeMap;
import com.server.map.packet.SM_MapInfo;
import com.server.map.packet.SM_MonsterInfo;
import com.server.map.resource.MapResource;
import com.server.monster.model.Monster;
import com.server.monster.resource.MonsterResource;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;
import com.server.user.fight.FightAccount;
import com.server.user.fight.syncStrategy.GridSyncStrategy;
import com.server.user.item.model.AbstractItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/4/28 20:14
 */
@Component
public class WorldService {

    private Logger logger = LoggerFactory.getLogger("地图日志");

    @Autowired
    private MapManager mapManager;

    @Autowired
    private ResourceManager resourceManager;

    /**
     * 初始化地图资源
     */
    public void initMap() {
        //初始化地图
        mapManager.initMap();
        //初始化刷怪
        mapManager.loadMonster();
    }

    /**
     * 切图
     *
     * @param account
     * @param targetMapId
     */
    public void changeMap(Account account, int targetMapId) {
        SpringContext.getSceneExecutorService().submit(ChangeMapCommand.valueOf(account, targetMapId));
    }

    /**
     * 切图
     *
     * @param account
     * @param targetMapId
     */
    public void serverChangeMap(Account account, int targetMapId) {
        MapResource mapResource = getMapResource(targetMapId);
        logger.info("账号[{}]进入地图：mapid[{}],mapname[{}]  当前坐标：{}_{}",
                account.getName(), targetMapId, mapResource.getName(),
                mapResource.getBornX(), mapResource.getBornY());

        int oldMapId = account.getMapId();
        leaveMap(account);
        enterMap(account, targetMapId);

        SM_ChangeMap packet = SM_ChangeMap.valueOf(account.getAccountId(), targetMapId, oldMapId);
        PacketSendUtil.send(account, packet);

    }


    /**
     * 进入地图
     *
     * @param account
     * @param targetMapId
     */
    public void enterMap(Account account, int targetMapId) {
        MapResource mapResource = getMapResource(targetMapId);
        MapInfo mapInfo = mapManager.getMapInfo(targetMapId);
        int gridX = mapResource.getBornX();
        int gridY = mapResource.getBornY();
        account.setMapId(targetMapId);
        account.setGridX(gridX);
        account.setGirdY(gridY);
        FightAccount fightAccount = FightAccount.valueOf(account);
        mapInfo.addFightAccount(fightAccount);
        account.setFightAccount(fightAccount.copy());
        logger.info("账号[{}]进入地图：{}", account.getName(), mapResource.getName());
        SpringContext.getAccountService().saveAccountInfo(account.getAccountId());

        char[][] mapInfos = mapInfo.printInfo();
        SM_MapInfo packet = SM_MapInfo.valueOf(mapInfos);
        PacketSendUtil.send(account, packet);
    }


    /**
     * 移动坐标
     *
     * @param account
     * @param targetGrid
     */
    public void move(Account account, Grid targetGrid) {
        MapResource mapResource = getMapResource(account.getMapId());
        int preX = account.getGridX();
        int preY = account.getGirdY();
        MapInfo mapInfo = getMapInfo(account.getMapId());

        if (isCanWalk(mapResource, targetGrid) && mapInfo.isCanWalk(targetGrid.getX(), targetGrid.getY())) {
            mapInfo.setGridAsRoad(preX, preY);
            int newX = targetGrid.getX();
            int newY = targetGrid.getY();
            account.setGridX(newX);
            account.setGirdY(newY);
            SpringContext.getAccountService().saveAccountInfo(account.getAccountId());
            account.fightSync(GridSyncStrategy.valueOf(Grid.valueOf(newX, newY)));
            SM_AccountMove packet = SM_AccountMove.valueOf(account.getAccountId(), newX, newY, preX, preY);
            PacketSendUtil.send(account, packet);
        } else {
            I18Utils.notifyMessage(account, I18nId.GRID_CAN_NOT_WALK);
        }
    }

    public boolean isCanWalk(MapResource mapResource, Grid grid) {
        MapInfo mapInfo = mapManager.getMapInfo(mapResource.getId());

        if (mapInfo != null) {
            int gridX = grid.getX();
            int gridY = grid.getY();
            //检查坐标合法性，是否越界
            mapResource.checkGrid(gridX, gridY);
            return mapResource.isWalkGrid(gridX, gridY) && mapInfo.isCanWalk(gridX, gridY);
        }


        return false;
    }

    /**
     * 离开地图
     */
    public void leaveMap(Account account) {

        int oldMapId = account.getMapId();
        MapInfo mapInfo = mapManager.getMapInfo(oldMapId);
        mapInfo.removeFightAccount(account.getAccountId());
    }

    public void printMapInfo(Account account) {
        int curMapid = account.getMapId();
        //记得传mapinfo
        MapInfo mapInfo = mapManager.getMapInfo(curMapid);
        mapInfo.printInfo();
    }

    public void printMapInfo(Account account, int mapId) {
        //记得传mapinfo
        MapInfo mapInfo = mapManager.getMapInfo(mapId);

        char[][] mapInfos = mapInfo.printInfo();
        SM_MapInfo packet = SM_MapInfo.valueOf(mapInfos);
        PacketSendUtil.send(account, packet);
    }

    /**
     * 打印指定地图 怪物信息
     *
     * @param account
     * @param mapId
     */
    public void printMonstersInfo(Account account, int mapId) {

        MapInfo mapInfo = mapManager.getMapInfo(mapId);
        List<Monster> monsters = mapInfo.getMonsters();
        if (monsters == null) {
            I18Utils.notifyMessage(account, I18nId.MAP_NULL_MONSTER);
            return;
        }
        SM_MonsterInfo packet = SM_MonsterInfo.valueOf(monsters, mapInfo.getMapName());
        PacketSendUtil.send(account, packet);
    }

    public MapResource getMapResource(int mapId) {
        Map<Integer, Object> mapResource = resourceManager.getResources(MapResource.class.getSimpleName());
        MapResource resource = (MapResource) mapResource.get(mapId);
        if (resource == null) {
            logger.error("MapResource找不到对应配置id：{0}" + mapId);
            return null;
        }
        return resource;
    }

    public MonsterResource getMonsterResource(int monsterId) {
        Map<Integer, Object> monsterResource = resourceManager.getResources(MonsterResource.class.getSimpleName());
        MonsterResource resource = (MonsterResource) monsterResource.get(monsterId);
        if (resource == null) {
            logger.error("MonsterResource找不到对应配置id：{0}" + monsterId);
            return null;
        }
        return resource;
    }

    /**
     * 击杀指定怪物 唯一id
     *
     * @param account
     * @param monsterGid
     */
    public void killMonster(Account account, long monsterGid) {
        int mapId = account.getMapId();
        MapInfo mapInfo = mapManager.getMapInfo(mapId);
        Monster monster = mapInfo.getMonsterByGid(monsterGid);
        if (monster == null) {
            I18Utils.notifyMessage(account, I18nId.NO_MONSTER);
            return;
        }
        mapInfo.removeMonster(monsterGid);
        Map<Integer, Integer> itemsMap = getMonsterResource(monster.getId()).getItems();
        if (itemsMap == null) {
            I18Utils.notifyMessageThrow(account, I18nId.MONSTER_NULL_DROP);
            return;
        }
        List<AbstractItem> items = SpringContext.getStoreService().createItems(itemsMap);

        if (SpringContext.getStoreService().isEnoughPackSize(account, items)) {
            SpringContext.getStoreService().addItemsToBag(account, items);
        }

        printMonstersInfo(account, account.getMapId());

    }

    public MapInfo getMapInfo(int mapId) {
        return mapManager.getMapInfo(mapId);
    }

    /**
     * 场景执行周期任务
     */
    public void doRateCommand() {

        Map<Integer, MapInfo> mapInfoMap = SpringContext.getMapManager().getMapInfos();
        for (int mapId : mapInfoMap.keySet()) {
            SpringContext.getSceneExecutorService().submit(SceneRateCommand.valueOf(null, mapId, 0, 500));
        }

    }


}
