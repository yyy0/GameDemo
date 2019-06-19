package com.server.user.skill.packet;

import java.io.Serializable;

/**
 * 设置技能至技能栏中
 *
 * @author yuxianming
 * @date 2019/6/13 11:31
 */
public class CM_SetSkillSlot implements Serializable {

    private int skillId;

    private int index;

    public static CM_SetSkillSlot valueOf(int skillId, int index) {
        CM_SetSkillSlot packet = new CM_SetSkillSlot();
        packet.skillId = skillId;
        packet.index = index;
        return packet;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
