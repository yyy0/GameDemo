package com.server.user.fight.packet;

import java.io.Serializable;

/**
 * 受击协议
 *
 * @author yuxianming
 * @date 2019/6/14 18:07
 */
public class SM_Hit implements Serializable {

    /**
     * 攻击者
     */
    private String accountId;

    /**
     * 使用技能
     */
    private int skillId;

    /**
     * 造成伤害
     */
    private long damage;

    /**
     * 技能名称
     */
    private String skillName;

    /**
     * 当前血量
     */
    private long curHP;

    /**
     * 生命上限
     */
    private long max_HP;

    public static SM_Hit valueOf(String accountId, String skillName, long damage, long curHP, long max_HP) {
        SM_Hit packet = new SM_Hit();
        packet.accountId = accountId;
        packet.skillName = skillName;
        packet.damage = damage;
        packet.curHP = curHP;
        packet.max_HP = max_HP;
        return packet;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public long getDamage() {
        return damage;
    }

    public void setDamage(long damage) {
        this.damage = damage;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
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
