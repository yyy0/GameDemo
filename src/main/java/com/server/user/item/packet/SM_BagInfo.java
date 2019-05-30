package com.server.user.item.packet;

import com.server.user.item.model.AbstractItem;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/29 15:55
 */
public class SM_BagInfo implements Serializable {

    private AbstractItem[] itemData;

    public AbstractItem[] getItemData() {
        return itemData;
    }

    public void setItemData() {
        this.itemData = itemData;
    }

    public static SM_BagInfo valueOf(AbstractItem[] items) {
        SM_BagInfo packet = new SM_BagInfo();
        packet.itemData = items;
        return packet;
    }
}
