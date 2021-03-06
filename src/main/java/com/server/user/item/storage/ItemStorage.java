package com.server.user.item.storage;

import com.server.user.item.constant.StorageConstant;
import com.server.user.item.model.AbstractItem;
import com.server.user.item.model.ResultItem;

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
        ItemStorage storage = new ItemStorage();
        storage.size = StorageConstant.BAG_MAXSIZE;
        storage.items = new AbstractItem[storage.size];
        return storage;
    }


    /**
     * 确保扩容（当格子数变大时的兼容处理）
     *
     * @param maxSize
     */
    public void ensureCapacity(int maxSize) {

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

    /**
     * 背包是否有指定道具
     *
     * @param modelId
     * @return
     */
    public boolean isExistItem(int modelId) {
        int length = items.length;

        for (int i = 0; i < length; i++) {
            if (items[i] != null) {
                if (items[i].getItemModelId() == modelId) {
                    return true;
                }
            }
        }
        return false;
    }

    public void clear() {
        items = new AbstractItem[size];
        this.num = 0;
    }

    /**
     * 添加道具
     *
     * @param item
     */
    public void addItem(AbstractItem item) {
        AbstractItem add = item;
        int overLimit = item.getOverLimit();
        if (overLimit > 1) {
            int length = items.length;
            for (int i = 0; i < length; i++) {
                if (items[i] != null && items[i].check(item) && items[i].getNum() < overLimit) {
                    ResultItem resultItem = items[i].add(item);
                    if (resultItem.isSuccess()) {
                        add = resultItem.getItem();
                    }
                }
                if (add == null) {
                    break;
                }
            }
        }
        if (add == null) {
            return;
        }
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = add;
                this.num++;
                break;
            }
        }
    }

    /**
     * 通过唯一id获取道具
     *
     * @param id
     * @return
     */
    public AbstractItem getItemByObjectId(long id) {
        int length = items.length;
        for (int i = 0; i < length; i++) {
            if (items[i] != null) {
                if (items[i].getObjectId() == id) {
                    return items[i];
                }
            }
        }
        return null;
    }

    /**
     * 整理背包 除去中间为null的道具  方法需优化
     */
    public void sortItem() {
        int length = items.length;
        for (int i = 0; i < length; i++) {
            if (items[i] == null) {
                for (int j = i; j < length - 1; j++) {
                    items[j] = items[j + 1];
                }
            }
        }
    }


    /**
     * 根据唯一id 扣除道具
     */
    public void reduceItem(long identifyId, int num) {
        int length = items.length;
        boolean isSort = false;
        for (int i = 0; i < length; i++) {
            if (items[i] != null) {
                if (items[i].getObjectId() == identifyId && items[i].getNum() >= num) {

                    int size = items[i].getNum() - num;
                    if (size == 0) {
                        items[i] = null;
                        isSort = true;
                        this.num--;
                    } else {
                        items[i].setNum(size);
                    }
                }
            } else {
                break;
            }
        }
        if (isSort) {
            sortItem();
        }
    }

    /**
     * 扣除道具
     * modelId 道具id
     * reduceNum 扣除数量
     */
    public void removeItemByModelId(int modelId, int reduceNum) {
        boolean isSort = false;
        if (getItemNum(modelId) >= reduceNum) {
            int length = items.length;
            for (int i = 0; i < length; i++) {
                if (items[i] != null && items[i].getItemModelId() == modelId) {

                    if (items[i].getNum() >= reduceNum) {
                        int size = items[i].getNum() - reduceNum;
                        if (size == 0) {
                            items[i] = null;
                            isSort = true;
                        } else {
                            items[i].setNum(size);
                        }
                        break;
                    } else {
                        reduceNum -= items[i].getNum();
                        items[i] = null;
                        this.num--;
                    }
                }
            }

        }
        if (isSort) {
            sortItem();
        }
    }

    /**
     * 根据道具id 获取背包的数量
     *
     * @param modelId
     * @return
     */
    public int getItemNum(int modelId) {
        int length = items.length;
        int num = 0;
        for (int i = 0; i < length; i++) {
            if (items[i] == null) {
                break;
            }
            if (items[i].getItemModelId() == modelId) {
                num += items[i].getNum();
            }
        }
        return num;
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

    public void printStorage() {
        for (AbstractItem item : items) {
            System.out.println("名称：" + item.getName() + ",数量:" + item.getNum());
        }
    }
}
