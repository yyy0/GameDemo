package com.server.user.skill.packet;

import java.io.Serializable;

/**
 * 学习技能
 *
 * @author yuxianming
 * @date 2019/6/12 11:15
 */
public class CM_StudySkill implements Serializable {

    private int skillId;

    public static CM_StudySkill valueOf(int skillId) {
        CM_StudySkill packet = new CM_StudySkill();
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
