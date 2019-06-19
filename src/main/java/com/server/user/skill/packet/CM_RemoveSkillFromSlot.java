package com.server.user.skill.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/13 11:34
 */
public class CM_RemoveSkillFromSlot implements Serializable {

    /**
     * 技能栏部位
     */
    private int index;

    public static CM_RemoveSkillFromSlot valueOf(int index) {
        CM_RemoveSkillFromSlot packet = new CM_RemoveSkillFromSlot();
        packet.index = index;
        return packet;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
