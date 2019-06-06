package com.server.user.equipUpgrade.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:37
 */
public class CM_EquipUpGrade implements Serializable {

    private int index;

    public static CM_EquipUpGrade valueOf(int index) {
        CM_EquipUpGrade packet = new CM_EquipUpGrade();
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
