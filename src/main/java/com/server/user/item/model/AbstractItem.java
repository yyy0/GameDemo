package com.server.user.item.model;

import com.SpringContext;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.server.common.identity.gameobject.GameObject;
import com.server.common.identity.service.IdentifyService;
import com.server.user.equipment.constant.EquipmentType;
import com.server.user.equipment.model.Equipment;
import com.server.user.item.constant.ItemType;
import com.server.user.item.resource.ItemResource;

/**
 * @author yuxianming
 * @date 2019/5/13 15:24
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Equipment.class, name = "equipment"),
        @JsonSubTypes.Type(value = CommonItem.class, name = "commonItem"),
        @JsonSubTypes.Type(value = Medicine.class, name = "medicine"),
        @JsonSubTypes.Type(value = Pandora.class, name = "pandora"),
})
public class AbstractItem extends GameObject implements Comparable<AbstractItem> {

    protected int itemModelId;

    protected int num;

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

    /**
     * 生成唯一id
     *
     * @return
     */
    private long createIdentifyId() {
        return SpringContext.getIdentifyService().getNextIdentify(IdentifyService.IdentifyType.ITEM);
    }

    @Override
    public String toString() {
        return "{" +
                "itemType=" + getItemType() +
                ", 唯一id=" + objectId +
                ", 道具id=" + itemModelId +
                ", 名称=" + getName() +
                ", 数量=" + num +
                '}';
    }

    public ItemResource getResource() {
        return SpringContext.getStoreService().getItemResource(itemModelId);
    }

    /**
     * 物品名称
     *
     * @return
     */
    public String getName() {
        return getResource().getName();
    }

    /**
     * 叠加数量
     *
     * @return
     */
    public int getOverLimit() {
        return getResource().getOverLimit();
    }

    /**
     * 物品类型
     *
     * @return
     */
    public ItemType getItemType() {
        return getResource().getItemType();
    }

    /**
     * 装备类型
     *
     * @return
     */
    public EquipmentType getEquipmentType() {
        return getResource().getEquipmentType();
    }

    /**
     * check是否为相同道具model
     */
    public boolean check(AbstractItem item) {
        return item.getItemModelId() == itemModelId;
    }

    /**
     * 叠加相同id的道具
     *
     * @param addItem
     */
    public ResultItem add(AbstractItem addItem) {
        int overLimit = addItem.getOverLimit();

        ResultItem resultItem = new ResultItem();
        //叠加数量大于1  道具数量小于叠加数量  道具id相同
        if (overLimit > 1 && this.num < overLimit && check(addItem)) {
            int curSize = this.num + addItem.getNum();
            if (curSize > overLimit) {
                curSize -= overLimit;
                this.setNum(overLimit);
                addItem.setNum(curSize);
                resultItem.setItem(addItem);
            } else {
                this.setNum(curSize);
            }
            resultItem.setSuccess(true);
            return resultItem;
        }
        return resultItem;
    }

    public boolean isEquipment() {
        return getItemType() == ItemType.EQUIPMENT;
    }

    @Override
    public int compareTo(AbstractItem o) {
        return 0;
    }


}
