package com.server.user.equipment.model;

import com.server.user.item.model.AbstractItem;

/**
 * @author yuxianming
 * @date 2019/5/13 20:34
 */
public class Equipment extends AbstractItem {


    @Override
    public String toString() {

        return "{" +
                "equipmentType=" + getEquipmentType() +
                ", 唯一id=" + objectId +
                ", 道具id=" + itemModelId +
                ", 名称=" + getName() +
                '}';
    }
}
