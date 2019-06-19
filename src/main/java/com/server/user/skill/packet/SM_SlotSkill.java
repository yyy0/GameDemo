package com.server.user.skill.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/13 14:37
 */
public class SM_SlotSkill implements Serializable {

    private int[] skills;

    public static SM_SlotSkill valueOf(int[] skills) {
        SM_SlotSkill packet = new SM_SlotSkill();
        packet.skills = skills;
        return packet;
    }

    public int[] getSkills() {
        return skills;
    }

    public void setSkills(int[] skills) {
        this.skills = skills;
    }
}
