package com.server.user.item.packet;

import com.server.user.item.model.AbstractItem;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/5 21:11
 */
public class SM_WareInfo implements Serializable {
    private AbstractItem[] itemData;

    public AbstractItem[] getItemData() {
        return itemData;
    }

    public void setItemData() {
        this.itemData = itemData;
    }

    public static SM_WareInfo valueOf(AbstractItem[] items) {
        SM_WareInfo packet = new SM_WareInfo();
        packet.itemData = items;
        return packet;
    }
}
