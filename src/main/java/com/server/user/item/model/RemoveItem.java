package com.server.user.item.model;

/**
 * @author yuxianming
 * @date 2019/5/30 15:57
 */
public class RemoveItem {

    private AbstractItem item;
    private int num;

    public static RemoveItem valueOf(AbstractItem item, int num) {
        RemoveItem removeItem = new RemoveItem();
        removeItem.item = item;
        removeItem.num = num;
        return removeItem;
    }

    public AbstractItem getItem() {
        return item;
    }

    public void setItem(AbstractItem item) {
        this.item = item;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
