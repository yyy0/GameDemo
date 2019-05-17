package com.server.user.item.resource;

import com.server.user.equipment.EquipmentType;
import com.server.user.item.constant.ItemType;

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
     * 堆叠数量 小于或等于1的为不可堆叠
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

    public static ItemResource valueOf(int id, String name, ItemType itemType,
                                       EquipmentType equipmentType,
                                       int overLimit, int quality, int storage,
                                       int effectValue, String effectTime) {
        ItemResource resource = ItemResource.valueOf(id, name, itemType, overLimit, quality, storage, effectValue, effectTime);
        resource.equipmentType = equipmentType;
        return resource;
    }

    public static ItemResource valueOf(int id, String name, ItemType itemType,
                                       int overLimit, int quality, int storage, int effectValue,
                                       String effectTime) {
        ItemResource resource = new ItemResource();
        resource.id = id;
        resource.name = name;
        resource.itemType = itemType;
        resource.overLimit = overLimit;
        resource.quality = quality;
        resource.storage = storage;
        resource.effectValue = effectValue;
        resource.effectTime = effectTime;
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
        if (overLimit < 1) {
            return 1;
        }
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

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }
}
