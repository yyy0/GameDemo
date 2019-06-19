package com.server.user.skill.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/13 11:32
 */
public class CM_RemoveSkill implements Serializable {

    private int skillId;

    public CM_RemoveSkill valueOf(int skillId) {
        CM_RemoveSkill packet = new CM_RemoveSkill();
        packet.skillId = skillId;
        return packet;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }
}
