package com.server.map.model;

import com.server.map.constant.MapConstant;
import com.server.map.resource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/4/29 0:04
 */
@Component
public class MapInfo {

    private Logger logger = LoggerFactory.getLogger(MapInfo.class);

    /**
     * 地图id
     */
    private int mapId;

    /**
     * 账号格子信息
     * String 账号id  grid 账号坐标
     */
    private Map<String, Grid> accountsGrid = new HashMap<>();

    /**
     * 地图信息（阻挡点，行走点，玩家坐标）
     */
    private char[][] mapInfo;

    private MapResource mapResource;

    public static MapInfo valueOf(int mapId, MapResource resource) {
        MapInfo mapInfo = new MapInfo();
        mapInfo.setMapId(mapId);
        mapInfo.mapResource = resource;
        mapInfo.initInfo();
        return mapInfo;
    }

    /**
     * 初始化地图资源
     */
    public void initInfo() {
        int[][] mapRes = mapResource.getMapRes();
        mapInfo = new char[mapResource.getHeight()][mapResource.getWidth()];
        for (int i = 0; i < mapInfo.length; i++) {
            for (int j = 0; j < mapInfo[i].length; j++) {
                if (mapRes[i][j] == 0) {
                    mapInfo[i][j] = MapConstant.WALL;
                } else if (mapRes[i][j] == 1) {
                    mapInfo[i][j] = MapConstant.ROAD;
                }
            }
        }
    }

    /**
     * 打印地图信息
     */
    public char[][] printInfo() {
        logger.info("【{}】地图信息：当前人数【{}】人", mapResource.getName(), accountsGrid.keySet().size());
        accountsGrid.forEach((accountId, grid) -> mapInfo[grid.getX()][grid.getY()] = MapConstant.USER);

        //绘制地图
        for (int i = 0; i < mapInfo.length; i++) {
            for (int j = 0; j < mapInfo[i].length; j++) {
                System.out.print(mapInfo[i][j]);
            }
            System.out.println();
        }

        return mapInfo;

    }

    public void addAccount(String accountId, Grid grid) {
        if (accountsGrid.containsKey(accountId)) {
            Grid gridTemp = accountsGrid.remove(accountId);
            mapInfo[gridTemp.getX()][gridTemp.getY()] = MapConstant.ROAD;
        }
        accountsGrid.put(accountId, grid);
    }

    public void removeAccount(String accountId) {
        Grid grid = accountsGrid.remove(accountId);
        mapInfo[grid.getX()][grid.getY()] = MapConstant.ROAD;
    }

    public Grid getAccountGrid(String accountId) {
        return accountsGrid.get(accountId);
    }


    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getAccountNum() {
        return accountsGrid.keySet().size();
    }
}
