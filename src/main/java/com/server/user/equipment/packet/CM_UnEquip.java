package com.server.user.equipment.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:21
 */
public class CM_UnEquip implements Serializable {

    /**
     * 装备部位
     */
    private int index;

    public static CM_UnEquip valueOf(int index) {
        CM_UnEquip packet = new CM_UnEquip();
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
