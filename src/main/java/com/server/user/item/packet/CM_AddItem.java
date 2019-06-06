package com.server.user.item.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 10:59
 */
public class CM_AddItem implements Serializable {
    private int itemModelId;
    private int num;

    public static CM_AddItem valueOf(int itemModelId, int num) {
        CM_AddItem packet = new CM_AddItem();
        packet.itemModelId = itemModelId;
        packet.num = num;
        return packet;
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
