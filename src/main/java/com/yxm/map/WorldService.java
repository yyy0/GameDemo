package com.yxm.map;

import com.SpringContext;
import com.yxm.user.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/4/28 20:14
 */
@Component
public class WorldService {


    private Logger logger = LoggerFactory.getLogger("地图日志");

    @Autowired
    private MapManager manager;

    /**
     * 初始化地图资源
     */
    public void initMap() {
        manager.initMap();
    }

    /**
     * 切图
     *
     * @param account
     * @param targetMapId
     */
    public void changeMap(Account account, int targetMapId) {
        MapResource mapResource = manager.getResource(targetMapId);
        logger.info("账号[{}]进入地图：mapid[{}],mapname[{}]  当前坐标：{}_{}",
                account.getName(), targetMapId, mapResource.getName(),
                mapResource.getBornX(), mapResource.getBornY());

        int oldMapId = account.getMapId();
        leaveMap(account, oldMapId);
        enterMap(account, targetMapId);

    }


    /**
     * 进入地图
     *
     * @param account
     * @param targetMapId
     */
    public void enterMap(Account account, int targetMapId) {
        MapResource mapResource = manager.getResource(targetMapId);
        MapInfo mapInfo = manager.getMapInfo(targetMapId);
        account.setMapId(targetMapId);
        account.setGridX(mapResource.getBornX());
        account.setGirdY(mapResource.getBornY());
        mapInfo.addAccount(account);
        logger.info("账号[{}]进入地图：{}", account.getName(), mapResource.getName());
        SpringContext.getAccountService().saveAccountInfo(account.getAccountId());
        //测试移动指令
        Grid grid = Grid.valueOf(5, 4);
        move(account, grid);
        mapInfo.printInfo();
    }


    /**
     * 移动坐标
     *
     * @param account
     * @param targetGrid
     */
    public void move(Account account, Grid targetGrid) {
        MapResource mapResource = manager.getResource(account.getMapId());
        if (isCanWalk(mapResource, targetGrid)) {
            account.setGridX(targetGrid.getX());
            account.setGirdY(targetGrid.getY());
        }
    }

    public boolean isCanWalk(MapResource mapResource, Grid grid) {
        int[][] mapres = mapResource.getMapres();
        if (mapres[grid.getX()][grid.getY()] == 0) {
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
        // MapResource mapResource=manager.getResource(oldMapId);
        MapInfo mapInfo = manager.getMapInfo(oldMapId);
        mapInfo.removeAccount(account);
    }

    public void printMapInfo(Account account) {
        int curMapid = account.getMapId();
        //记得传mapinfo
        MapInfo mapInfo = manager.getMapInfo(curMapid);
        mapInfo.printInfo();
    }

    public MapResource getMapResource(int mapId) {
        return manager.getResource(mapId);
    }
}
