package com.server.map.model;

import com.SpringContext;
import com.server.map.constant.MapConstant;
import com.server.map.resource.MapResource;
import com.server.monster.model.Monster;
import com.server.user.fight.FightAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * 怪物信息
     */
    private List<Monster> monsters = new ArrayList<>();

    /**
     * 场景账号
     */
    private Map<String, FightAccount> fightAccountMap = new HashMap<>();

    /**
     * 地图图标信息（阻挡点，行走点，玩家坐标，怪物）
     */
    private char[][] mapChars;

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
        mapChars = new char[mapResource.getHeight()][mapResource.getWidth()];
        for (int i = 0; i < mapChars.length; i++) {
            for (int j = 0; j < mapChars[i].length; j++) {
                if (mapRes[i][j] == 0) {
                    mapChars[i][j] = MapConstant.WALL;
                } else if (mapRes[i][j] == 1) {
                    mapChars[i][j] = MapConstant.ROAD;
                }
            }
        }
    }

    /**
     * 打印地图信息
     */
    public char[][] printInfo() {
        logger.info("【{}】地图信息：当前人数【{}】人", mapResource.getName(), accountsGrid.keySet().size());
        fightAccountMap.forEach((accountId, fightAccount) -> mapChars[fightAccount.getGrid().getX()][fightAccount.getGrid().getY()] = MapConstant.USER);

        logger.info("【{}】地图信息：当前怪物数量【{}】个", mapResource.getName(), monsters.size());
        monsters.forEach((monster) -> mapChars[monster.getGridX()][monster.getGridY()] = MapConstant.MONSTER);
        //绘制地图
        for (int i = 0; i < mapChars.length; i++) {
            for (int j = 0; j < mapChars[i].length; j++) {
                System.out.print(mapChars[i][j]);
            }
            System.out.println();
        }

        return mapChars;

    }

    /**
     * @param monster
     */
    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public void removeMonster(long monsterGid) {
        for (Monster monster : monsters) {
            if (monster.getObjectId() == monsterGid) {
                monsters.remove(monster);
                mapChars[monster.getGridX()][monster.getGridY()] = MapConstant.ROAD;
                return;
            }
        }
    }

    public void addAccount(String accountId, Grid grid) {
        if (accountsGrid.containsKey(accountId)) {
            Grid gridTemp = accountsGrid.remove(accountId);
            mapChars[gridTemp.getX()][gridTemp.getY()] = MapConstant.ROAD;
        }
        accountsGrid.put(accountId, grid);
    }

    public void addFightAccount(FightAccount fightAccount) {
        String accountId = fightAccount.getAccountId();
        if (fightAccountMap.containsKey(accountId)) {
            Grid gridTemp = fightAccountMap.remove(accountId).getGrid();
            mapChars[gridTemp.getX()][gridTemp.getY()] = MapConstant.ROAD;
        }

        fightAccountMap.put(accountId, fightAccount);
    }

    public Monster getMonsterByGid(long gid) {
        for (Monster monster : monsters) {
            if (monster.getObjectId() == gid) {
                return monster;
            }
        }
        return null;
    }

    public void removeAccount(String accountId) {
        Grid grid = accountsGrid.remove(accountId);
        mapChars[grid.getX()][grid.getY()] = MapConstant.ROAD;
    }

    public void removeFightAccount(String accountId) {
        FightAccount fightAccount = fightAccountMap.remove(accountId);
        if (fightAccount == null) {
            return;
        }
        Grid grid = fightAccount.getGrid();
        mapChars[grid.getX()][grid.getY()] = MapConstant.ROAD;
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

    public boolean isCanWalk(int gridX, int gridY) {
        return mapChars[gridX][gridY] == MapConstant.ROAD;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public MapResource getMapResource() {
        return SpringContext.getWorldService().getMapResource(mapId);
    }

    public String getMapName() {
        return getMapResource().getName();
    }

    public FightAccount getFightAccount(String accountId) {
        return fightAccountMap.get(accountId);
    }

    public int getFightAccountNum() {
        return fightAccountMap.size();
    }

    public void setGridAsRoad(int gridX, int gridY) {
        mapChars[gridX][gridY] = MapConstant.ROAD;
    }


    /**
     * 获取指定战斗账号周围的角色
     *
     * @param fightAccount
     * @param range
     * @return
     */
    public List<FightAccount> getAroundFightAccount(FightAccount fightAccount, int range) {

        Grid selfGrid = fightAccount.getGrid();
        List<FightAccount> list = new ArrayList<>(fightAccountMap.size());
        for (FightAccount around : fightAccountMap.values()) {
            // 除去自身
            if (around.getAccountId().equals(fightAccount.getAccountId())) {
                continue;
            }
            if (selfGrid.isInRange(around.getGrid(), range)) {
                list.add(around);
            }
        }
        return list;
    }

    public Map<String, FightAccount> getFightAccountMap() {
        return fightAccountMap;
    }

    public void setFightAccountMap(Map<String, FightAccount> fightAccountMap) {
        this.fightAccountMap = fightAccountMap;
    }
}
