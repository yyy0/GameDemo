package com.server.map.packet;

import java.io.Serializable;

/**
 * 使用技能攻击怪物
 *
 * @author yuxianming
 * @date 2019/7/2 14:29
 */
public class CM_HitMonster implements Serializable {

    private int skillId;

    /**
     * 怪物唯一id
     */
    private long monsterGid;

    public static CM_HitMonster valueOf(int skillId, long monsterGid) {
        CM_HitMonster packet = new CM_HitMonster();
        packet.monsterGid = monsterGid;
        packet.skillId = skillId;
        return packet;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public long getMonsterGid() {
        return monsterGid;
    }

    public void setMonsterGid(long monsterGid) {
        this.monsterGid = monsterGid;
    }
}
