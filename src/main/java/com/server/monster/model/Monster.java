package com.server.monster.model;

import com.SpringContext;
import com.server.common.identity.gameobject.GameObject;
import com.server.common.identity.service.IdentifyService;
import com.server.monster.resource.MonsterResource;

/**
 * @author yuxianming
 * @date 2019/6/11 11:51
 */
public class Monster extends GameObject {

    /**
     * 怪物id
     */
    private int id;

    private String name;

    private int level;

    /**
     * 当前血量
     */
    private int curHp;

    /**
     * 最大生命值
     */
    private int maxHp;

    private int atk;

    private int def;

    private int gridX;

    private int gridY;

    public static Monster valueOf(MonsterResource resource) {
        Monster monster = new Monster();
        monster.atk = resource.getAtk();
        monster.maxHp = resource.getMaxHp();
        monster.def = resource.getDef();
        monster.level = resource.getLevel();
        monster.id = resource.getId();
        monster.name = resource.getName();
        monster.curHp = monster.maxHp;
        monster.objectId = SpringContext.getIdentifyService().getNextIdentify(IdentifyService.IdentifyType.MONSTER);
        return monster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurHp() {
        return curHp;
    }

    public void setCurHp(int curHp) {
        this.curHp = curHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    @Override
    public String toString() {
        return "怪物信息:" + "\r\n" +
                "唯一id=" + objectId +
                "   怪物id=" + id + "\r\n" +
                "名字：" + name + "\r\n" +
                "等级：" + level + "\r\n" +
                "当前血量：" + curHp + "\r\n" +
                "生命上限：" + maxHp + "\r\n" +
                "攻击力:" + atk + "\r\n" +
                "防御力：" + def + "\r\n" +
                "坐标：" + gridX + "_" + gridY;
    }
}
