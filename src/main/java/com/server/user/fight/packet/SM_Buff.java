package com.server.user.fight.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/18 20:56
 */
public class SM_Buff implements Serializable {

    /**
     * 造成伤害
     */
    private long damage;

    /**
     * 技能名称
     */
    private String buffName;

    /**
     * 当前血量
     */
    private long curHP;

    /**
     * 生命上限
     */
    private long max_HP;

    public static SM_Buff valueOf(String buffName, long curHP, long max_HP, long damage) {
        SM_Buff packet = new SM_Buff();
        packet.buffName = buffName;
        packet.curHP = curHP;
        packet.max_HP = max_HP;
        packet.damage = damage;
        return packet;
    }

    public long getDamage() {
        return damage;
    }

    public void setDamage(long damage) {
        this.damage = damage;
    }

    public String getBuffName() {
        return buffName;
    }

    public void setBuffName(String buffName) {
        this.buffName = buffName;
    }

    public long getCurHP() {
        return curHP;
    }

    public void setCurHP(long curHP) {
        this.curHP = curHP;
    }

    public long getMax_HP() {
        return max_HP;
    }

    public void setMax_HP(long max_HP) {
        this.max_HP = max_HP;
    }
}
