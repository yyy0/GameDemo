package com.yxm.user.item.resource;

import com.yxm.user.equipment.EquipmentType;
import com.yxm.user.item.constant.ItemType;

/**
 * @author yuxianming
 * @date 2019/5/10 18:10
 */
public class ItemResource {

    /**
     * 物品id
     */
    private int id;
    /**
     * 物品名称
     */
    private String name;
    /**
     * 物品类型
     */
    private ItemType itemType;
    /**
     * 装备类型
     */
    private EquipmentType equipmentType;
    /**
     * 堆叠数量
     */
    private int overLimit;
    /**
     * 物品品质
     */
    private int quality;
    /**
     * 是否可以进入仓库
     */
    private int storage;
    /**
     * 使用效果值
     */
    private int effectValue;
    /**
     * 使用时间
     */
    private String effectTime;

    public static ItemResource valueOf(String id, String name, ItemType itemType,
                                       EquipmentType equipmentType,
                                       int overLimit, int quality, int storage,
                                       int effectValue, String effectTime) {
        ItemResource resource = new ItemResource();

        return resource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOverLimit() {
        return overLimit;
    }

    public void setOverLimit(int overLimit) {
        this.overLimit = overLimit;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public void setEffectValue(int effectValue) {
        this.effectValue = effectValue;
    }

    public String getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(String effectTime) {
        this.effectTime = effectTime;
    }
}
