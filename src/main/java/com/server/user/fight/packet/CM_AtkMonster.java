package com.server.user.fight.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/20 20:58
 */
public class CM_AtkMonster implements Serializable {

    private int skillId;

    /**
     * 怪物唯一id
     */
    private long monsterGid;

    public static CM_AtkMonster valueOf(int skillId, long monsterGid) {
        CM_AtkMonster packet = new CM_AtkMonster();
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

    public void setMonsterGid(int monsterGid) {
        this.monsterGid = monsterGid;
    }
}
