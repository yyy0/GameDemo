package com.server.map.service;

import com.SpringContext;
import com.server.common.command.SceneRateCommand;
import com.server.common.resource.ResourceManager;
import com.server.map.command.ChangeMapCommand;
import com.server.map.command.EnterMapCommand;
import com.server.map.command.MapInfoCommand;
import com.server.map.command.MapMoveCommand;
import com.server.map.handler.AbstractMapHandler;
import com.server.map.model.Grid;
import com.server.map.model.Scene;
import com.server.map.packet.SM_AccountMove;
import com.server.map.packet.SM_ChangeMap;
import com.server.map.packet.SM_MapInfo;
import com.server.map.packet.SM_MonsterInfo;
import com.server.map.resource.MapResource;
import com.server.monster.model.Monster;
import com.server.monster.resource.MonsterAreaResource;
import com.server.monster.resource.MonsterResource;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;
import com.server.user.fight.FightAccount;
import com.server.user.fight.command.FightAtkMonsterCommand;
import com.server.user.fight.syncStrategy.GridSyncStrategy;
import com.server.user.item.model.AbstractItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        logger.info("账号[{}]进入地图：mapId[{}],mapName[{}]  当前坐标：{}_{}",
                account.getName(), targetMapId, mapResource.getName(),
                mapResource.getBornX(), mapResource.getBornY());

        int oldMapId = account.getMapId();
        MapResource oldMapResource = getMapResource(oldMapId);
        AbstractMapHandler oldMapHandler = AbstractMapHandler.getMapHandler(oldMapResource.getType());
        oldMapHandler.leaveMap(account);
        SpringContext.getSceneExecutorService().submit(EnterMapCommand.valueOf(account, targetMapId));

    }

    /**
     * 进入地图
     *
     * @param account
     * @param targetMapId
     */
    public void enterMap(Account account, int targetMapId) {
        MapResource mapResource = getMapResource(targetMapId);
        Scene scene = mapManager.getMapInfo(targetMapId);

        account.setOldMapId(account.getMapId());
        account.setMapId(targetMapId);
        account.setGridX(mapResource.getBornX());
        account.setGirdY(mapResource.getBornY());
        FightAccount fightAccount = FightAccount.valueOf(account);
        scene.addFightAccount(fightAccount);
        account.setFightAccount(fightAccount.copy());

        logger.info("账号[{}]进入地图：{}", account.getName(), mapResource.getName());
        SpringContext.getAccountService().saveAccountInfo(account);
        SM_ChangeMap packet = SM_ChangeMap.valueOf(account.getAccountId(), targetMapId, account.getOldMapId());
        PacketSendUtil.send(account, packet);

        SM_MapInfo mapInfoPacket = SM_MapInfo.valueOf(scene);
        PacketSendUtil.send(account, mapInfoPacket);
    }

    public void doMove(Account account, Grid targetGrid) {
        MapResource mapResource = getMapResource(account.getMapId());
        int preX = account.getGridX();
        int preY = account.getGirdY();
        Scene scene = getMapInfo(account, account.getMapId());

        if (isCanWalk(mapResource, targetGrid) && scene.isCanWalk(targetGrid.getX(), targetGrid.getY())) {
            scene.setGridAsRoad(preX, preY);
            int newX = targetGrid.getX();
            int newY = targetGrid.getY();
            account.setGridX(newX);
            account.setGirdY(newY);
            SpringContext.getAccountService().saveAccountInfo(account.getAccountId());
            account.fightSync(GridSyncStrategy.valueOf(Grid.valueOf(newX, newY)));
            SpringContext.getSceneExecutorService().submit(MapInfoCommand.valueOf(account, scene.getMapId()));
            SM_AccountMove packet = SM_AccountMove.valueOf(account.getAccountId(), newX, newY, preX, preY);
            PacketSendUtil.send(account, packet);

        } else {
            I18Utils.notifyMessage(account, I18nId.GRID_CAN_NOT_WALK);
        }
    }

    /**
     * 移动坐标
     *
     * @param account
     * @param targetGrid
     */
    public void move(Account account, Grid targetGrid) {
        SpringContext.getSceneExecutorService().submit(MapMoveCommand.valueOf(account, targetGrid));
    }

    public boolean isCanWalk(MapResource mapResource, Grid grid) {
        Scene scene = mapManager.getMapInfo(mapResource.getId());

        if (scene != null) {
            int gridX = grid.getX();
            int gridY = grid.getY();
            //检查坐标合法性，是否越界
            mapResource.checkGrid(gridX, gridY);
            return mapResource.isWalkGrid(gridX, gridY) && scene.isCanWalk(gridX, gridY);
        }
        return false;
    }

    /**
     * 离开地图
     */
    public void leaveMap(Account account) {

        int oldMapId = account.getMapId();
        Scene scene = mapManager.getMapInfo(oldMapId);
        scene.removeFightAccount(account.getAccountId());
    }

    public void printMapInfo(Account account, int mapId) {
        SpringContext.getSceneExecutorService().submit(MapInfoCommand.valueOf(account, mapId));
    }

    /**
     * 若mapId 为0  打印account当前地图信息
     *
     * @param account
     * @param mapId
     */
    public void doPrintMapInfo(Account account, int mapId) {

        int id = mapId == 0 ? account.getMapId() : mapId;
        MapResource mapResource = getMapResource(id);
        AbstractMapHandler mapHandler = AbstractMapHandler.getMapHandler(mapResource.getType());

        Scene scene = mapHandler.getMapInfo(account, id);
        SM_MapInfo packet = SM_MapInfo.valueOf(scene);
        PacketSendUtil.send(account, packet);
    }

    /**
     * 打印指定地图 怪物信息
     *
     * @param account
     * @param mapId
     */
    public void printMonstersInfo(Account account, int mapId) {

        Scene scene = getMapInfo(account, mapId);
        List<Monster> monsters = scene.getMonsters();
        if (monsters == null) {
            I18Utils.notifyMessage(account, I18nId.MAP_NULL_MONSTER);
            return;
        }
        SM_MonsterInfo packet = SM_MonsterInfo.valueOf(monsters, scene.getMapName());
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

    public List<MonsterAreaResource> getMonsterAreaResources(int mapId) {
        Map<Integer, Object> monsterAreaResource = resourceManager.getResources(MonsterAreaResource.class.getSimpleName());
        List<MonsterAreaResource> areaResources = new ArrayList<>(monsterAreaResource.size());
        for (Object object : monsterAreaResource.values()) {
            MonsterAreaResource areaResource = (MonsterAreaResource) object;
            if (areaResource.getMapId() == mapId) {
                areaResources.add(areaResource);
            }
        }
        return areaResources;
    }

    /**
     * 击杀指定怪物 唯一id
     *
     * @param account
     * @param monsterGid
     */
    public void killMonster(Account account, long monsterGid) {
        int mapId = account.getMapId();
        Scene scene = getMapInfo(account, mapId);
        Monster monster = scene.getMonsterByGid(monsterGid);
        if (monster == null) {
            I18Utils.notifyMessage(account, I18nId.NO_MONSTER);
            return;
        }
        scene.removeMonster(monsterGid);
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

    /**
     * 获取mapinfo
     *
     * @param account
     * @param mapId   为0时获取 account当前mapId
     * @return
     */
    public Scene getMapInfo(Account account, int mapId) {
        int id = mapId == 0 ? account.getMapId() : mapId;
        MapResource resource = getMapResource(id);
        AbstractMapHandler mapHandler = AbstractMapHandler.getMapHandler(resource.getType());
        return mapHandler.getMapInfo(account, id);
    }

    /**
     * 场景执行周期任务
     */
    public void doRateCommand() {

        Map<Integer, Scene> mapInfoMap = SpringContext.getMapManager().getMapInfos();
        for (int mapId : mapInfoMap.keySet()) {
            SpringContext.getSceneExecutorService().submit(SceneRateCommand.valueOf(null, mapId, 0, 500));
        }

    }


    /**
     * 使用技能攻击怪物
     *
     * @param account
     * @param skillId
     * @param monsterGid
     */
    public void hitMonster(Account account, int skillId, long monsterGid) {
        SpringContext.getSceneExecutorService().submit(FightAtkMonsterCommand.valueOf(account, skillId, monsterGid));
    }

    public void handlerDeadMonster(Account account, Monster monster) {
        if (account == null || monster == null) {
            return;
        }
        Scene scene = mapManager.getMapInfo(account.getMapId());
        scene.removeMonster(monster.getObjectId());

    }


}
