package com.server.user.skill.packet;

import java.io.Serializable;
import java.util.Set;

/**
 * @author yuxianming
 * @date 2019/6/13 14:35
 */
public class SM_AllSkill implements Serializable {

    private Set<Integer> allSkill;

    public static SM_AllSkill valueOf(Set<Integer> allSkill) {
        SM_AllSkill packet = new SM_AllSkill();
        packet.allSkill = allSkill;
        return packet;
    }

    public Set<Integer> getAllSkill() {
        return allSkill;
    }

    public void setAllSkill(Set<Integer> allSkill) {
        this.allSkill = allSkill;
    }
}
