package com.server.map.model;

import com.SpringContext;
import com.server.common.command.ICommand;
import com.server.map.constant.MapConstant;
import com.server.map.resource.MapResource;
import com.server.monster.model.Monster;
import com.server.monster.resource.MonsterAreaResource;
import com.server.monster.resource.MonsterResource;
import com.server.tool.TimeUtil;
import com.server.user.fight.FightAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author yuxianming
 * @date 2019/4/29 0:04
 */
@Component
public class Scene {

    private Logger logger = LoggerFactory.getLogger(Scene.class);

    /**
     * 地图id
     */
    private int mapId;

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

    /**
     * 地图创建时间
     */
    private long createTime;

    /**
     * 定时command 用于指定条件时取消定时任务
     */
    private Map<Class<? extends ICommand>, ICommand> commandMap = new HashMap<>();

    public static Scene valueOf(int mapId, MapResource resource) {
        Scene scene = new Scene();
        scene.setMapId(mapId);
        scene.mapResource = resource;
        scene.initInfo();
        scene.createTime = TimeUtil.now();
        return scene;
    }

    /**
     * 加载怪物
     */
    public void initMonster() {
        List<MonsterAreaResource> areaResources = SpringContext.getWorldService().getMonsterAreaResources(mapId);
        for (MonsterAreaResource areaResource : areaResources) {
            int monsterId = areaResource.getMonsterId();
            MonsterResource monsterResource = SpringContext.getWorldService().getMonsterResource(monsterId);
            Monster monster = Monster.valueOf(monsterResource);
            monster.setGridX(areaResource.getGridX());
            monster.setGridY(areaResource.getGridY());
            //将怪物放入地图信息中
            this.addMonster(monster);
        }
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
        logger.info("【{}】地图信息：当前人数【{}】人", mapResource.getName(), fightAccountMap.size());
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
        Iterator<Monster> iterator = monsters.iterator();
        while (iterator.hasNext()) {
            Monster monster = iterator.next();
            if (monster.getObjectId() == monsterGid) {
                iterator.remove();
                mapChars[monster.getGridX()][monster.getGridY()] = MapConstant.ROAD;
                return;
            }
        }

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

    public void removeFightAccount(String accountId) {
        FightAccount fightAccount = fightAccountMap.remove(accountId);
        if (fightAccount == null) {
            return;
        }
        Grid grid = fightAccount.getGrid();
        mapChars[grid.getX()][grid.getY()] = MapConstant.ROAD;
    }


    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
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


    /**
     * 获取指定战斗账号周围的角色，指定数量
     *
     * @param fightAccount
     * @param range
     * @param num
     * @return
     */
    public List<FightAccount> getAroundFightAccount(FightAccount fightAccount, int range, int num) {

        Grid selfGrid = fightAccount.getGrid();
        List<FightAccount> list = new ArrayList<>(fightAccountMap.size());
        for (FightAccount around : fightAccountMap.values()) {
            // 除去自身
            if (around.getAccountId().equals(fightAccount.getAccountId())) {
                continue;
            }
            if (selfGrid.isInRange(around.getGrid(), range)) {
                list.add(around);
                num--;
                if (num == 0) {
                    break;
                }
            }
        }
        return list;
    }

    public List<Monster> getAroundMonster(FightAccount fightAccount, int range, int num, long monsterGid) {

        Grid selfGrid = fightAccount.getGrid();
        List<Monster> list = new ArrayList<>(monsters.size());
        for (Monster monster : monsters) {
            // 除去自身
            if (monster.getObjectId() == monsterGid) {
                continue;
            }
            if (selfGrid.isInRange(monster.getGrid(), range)) {
                list.add(monster);
                num--;
                if (num == 0) {
                    break;
                }
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

    public boolean isEmptyMonster() {
        return monsters.size() == 0;
    }

    public ICommand getCommand(Class<? extends ICommand> command) {
        return commandMap.get(command);
    }

    public void addCommand(ICommand command) {

        //如果有同类型的command 取消已有command
        ICommand existCommand = getCommand(command.getClass());
        if (existCommand != null) {
            existCommand.cancel();
        }
        commandMap.put(command.getClass(), command);
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Map<Class<? extends ICommand>, ICommand> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<Class<? extends ICommand>, ICommand> commandMap) {
        this.commandMap = commandMap;
    }
}
