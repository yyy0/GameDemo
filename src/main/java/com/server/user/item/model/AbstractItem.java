package com.server.user.item.model;

import com.SpringContext;
import com.server.user.item.constant.ItemType;
import com.server.user.item.resource.ItemResource;

/**
 * @author yuxianming
 * @date 2019/5/13 15:24
 */
public class AbstractItem {

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

    @Override
    public String toString() {

        ItemResource resource = SpringContext.getItemService().getItemResource(itemModelId);
        return "AbstractItem{" +
                "itemType=" + resource.getItemType() +
                "itemModelId=" + itemModelId +
                ", name=" + resource.getName() +
                ", num=" + num +
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
     * check是否为相同道具model
     */
    public boolean check(AbstractItem item) {
        return item.getItemModelId() == itemModelId;
    }

    /**
     * 同类物品叠加
     * @param item
     * @return
     */
//    public AbstractItem add(AbstractItem item){
//
//    }
}
