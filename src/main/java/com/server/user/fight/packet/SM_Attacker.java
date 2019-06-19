package com.server.user.fight.packet;

import java.io.Serializable;

/**
 * 攻击者信息
 *
 * @author yuxianming
 * @date 2019/6/17 20:26
 */
public class SM_Attacker implements Serializable {

    /**
     * 受击者
     */
    private String victim;

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

    public static SM_Attacker valueOf(String accountId, int skillId, String skillName, long damage) {
        SM_Attacker packet = new SM_Attacker();
        packet.victim = accountId;
        packet.skillId = skillId;
        packet.skillName = skillName;
        packet.damage = damage;
        return packet;
    }


    public String getVictim() {
        return victim;
    }

    public void setVictim(String victim) {
        this.victim = victim;
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
}
