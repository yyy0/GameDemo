package com.server.user.item.storage;

import com.server.user.item.constant.StorageConstant;
import com.server.user.item.model.AbstractItem;

/**
 * 背包
 *
 * @author yuxianming
 * @date 2019/5/13 17:04
 */
public class ItemStorage {

    /**
     * 最大格子数
     */
    private int size;

    /**
     * 背包里的道具实例
     */
    private AbstractItem[] items;

    /**
     * 已使用格子数
     */
    private int num;

    public static ItemStorage valueOf() {
        int maxSize = StorageConstant.BAG_MAXSIZE;
        ItemStorage storage = new ItemStorage(maxSize);
        return storage;
    }

    public ItemStorage(int size) {
        this.size = size;
        items = new AbstractItem[size];
    }

    /**
     * 确保扩容（当格子数变大时的兼容处理）
     *
     * @param maxSize
     */
    public void ensureCapacity(int maxSize) {
        int size = maxSize;
        if (maxSize > getSize()) {
            AbstractItem[] newItems = new AbstractItem[maxSize];
            System.arraycopy(getItems(), 0, newItems, 0, getItems().length);
            setItems(newItems);
            setSize(maxSize);
            resetNum(newItems);
        }
    }

    public void resetNum(AbstractItem[] items) {
        int numSize = 0;
        for (AbstractItem item : items) {
            if (item != null) {
                numSize++;
            }
        }
        this.num = numSize;
    }

    public void addItem(AbstractItem item) {

        int overLimit = item.getOverLimit();
        if (overLimit > 1) {
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null && items[i].check(item)) {

                }
            }
        }

        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                this.num++;
                break;
            }
        }
    }

    public int getEmptySize() {
        return this.size - this.num;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public AbstractItem[] getItems() {
        return items;
    }

    public void setItems(AbstractItem[] items) {
        this.items = items;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
