package com.server.map.service;

import com.SpringContext;
import com.server.common.resource.ResourceManager;
import com.server.map.model.Grid;
import com.server.map.model.MapInfo;
import com.server.map.packet.SM_AccountMove;
import com.server.map.packet.SM_ChangeMap;
import com.server.map.resource.MapResource;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/4/28 20:14
 */
@Component
public class WorldService {


    private Logger logger = LoggerFactory.getLogger("地图日志");

    @Autowired
    private MapManager manager;

    @Autowired
    private ResourceManager resourceManager;

    /**
     * 初始化地图资源
     */
    public void initMap() {
        manager.initMap();
    }

    /**
     * 切图
     * @param account
     * @param targetMapId
     */
    public void changeMap(Account account, int targetMapId) {
        MapResource mapResource = getMapResource(targetMapId);
        logger.info("账号[{}]进入地图：mapid[{}],mapname[{}]  当前坐标：{}_{}",
                account.getName(), targetMapId, mapResource.getName(),
                mapResource.getBornX(), mapResource.getBornY());

        int oldMapId = account.getMapId();
        leaveMap(account, oldMapId);
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
        MapInfo mapInfo = manager.getMapInfo(targetMapId);
        int gridX = mapResource.getBornX();
        int gridY = mapResource.getBornY();
        account.setMapId(targetMapId);
        account.setGridX(gridX);
        account.setGirdY(gridY);
        mapInfo.addAccount(account.getAccountId(), Grid.valueOf(gridX, gridY));
        logger.info("账号[{}]进入地图：{}", account.getName(), mapResource.getName());
        SpringContext.getAccountService().saveAccountInfo(account.getAccountId());
        //测试移动指令

        mapInfo.printInfo();
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
        MapInfo mapInfo = manager.getMapInfo(account.getMapId());
        if (isCanWalk(mapResource, targetGrid)) {
            int newX = targetGrid.getX();
            int newY = targetGrid.getY();
            account.setGridX(newX);
            account.setGirdY(newY);
            SpringContext.getAccountService().saveAccountInfo(account.getAccountId());
            mapInfo.addAccount(account.getAccountId(), Grid.valueOf(newX, newY));
            SM_AccountMove packet = SM_AccountMove.valueOf(account.getAccountId(), newX, newY, preX, preY);
            PacketSendUtil.send(account, packet);
        }
    }

    public boolean isCanWalk(MapResource mapResource, Grid grid) {
        int[][] mapres = mapResource.getMapRes();
        int gridX = grid.getX();
        int gridY = grid.getY();
        //检查坐标合法性，是否越界
        mapResource.checkGrid(gridX, gridY);
        if (mapres[gridX][gridY] == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 离开地图
     */
    public void leaveMap(Account account, int targetMapid) {

        int oldMapId = account.getMapId();
        MapInfo mapInfo = manager.getMapInfo(oldMapId);
        mapInfo.removeAccount(account.getAccountId());
    }

    public void printMapInfo(Account account) {
        int curMapid = account.getMapId();
        //记得传mapinfo
        MapInfo mapInfo = manager.getMapInfo(curMapid);
        mapInfo.printInfo();
    }

    public void printMapInfo(int mapId) {
        //记得传mapinfo
        MapInfo mapInfo = manager.getMapInfo(mapId);
        mapInfo.printInfo();
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
}
