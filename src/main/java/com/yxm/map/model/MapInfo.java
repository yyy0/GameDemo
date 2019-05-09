package com.yxm.map.model;

import com.SpringContext;
import com.yxm.map.constant.MapConstant;
import com.yxm.map.resource.MapResource;
import com.yxm.user.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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
     * 账号信息
     */
    private Set<Account> accounts = new HashSet<>();

    /**
     * 地图信息（阻挡点，行走点，玩家坐标）
     */
    private char[][] mapInfo;

    private MapResource mapResource;

    public static MapInfo valueOf(int mapId) {
        MapInfo mapInfo = new MapInfo();
        mapInfo.setMapId(mapId);
        mapInfo.mapResource = SpringContext.getWorldService().getMapResource(mapId);
        mapInfo.initInfo();
        return mapInfo;
    }

    /**
     * 初始化地图资源
     */
    public void initInfo() {
        int[][] mapRes = mapResource.getMapres();
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
    public void printInfo() {
        logger.info("【{}】地图信息：当前人数【{}】人", mapResource.getName(), accounts.size());
        for (Account account : accounts) {
            mapInfo[account.getGirdY()][account.getGirdY()] = MapConstant.USER;
        }

        //绘制地图
        for (int i = 0; i < mapInfo.length; i++) {
            for (int j = 0; j < mapInfo[i].length; j++) {
                System.out.print(mapInfo[i][j]);
            }
            System.out.println();
        }
        //重置坐标点为行走点
        for (Account account : accounts) {
            mapInfo[account.getGirdY()][account.getGirdY()] = MapConstant.ROAD;
        }
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
