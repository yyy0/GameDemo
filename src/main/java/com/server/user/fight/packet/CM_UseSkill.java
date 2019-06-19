package com.server.user.fight.packet;

import java.io.Serializable;

/**
 * 使用技能
 *
 * @author yuxianming
 * @date 2019/6/14 18:02
 */
public class CM_UseSkill implements Serializable {

    private String targetAccountId;

    private int skillId;

    public static CM_UseSkill valueOf(String targetAccountId, int skillId) {
        CM_UseSkill packet = new CM_UseSkill();
        packet.targetAccountId = targetAccountId;
        packet.skillId = skillId;
        return packet;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }
}
