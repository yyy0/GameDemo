package com.server.user.item.service;

import com.server.common.entity.CommonManager;
import com.server.common.resource.ResourceManager;
import com.server.user.equipment.constant.EquipmentType;
import com.server.user.item.constant.ItemType;
import com.server.user.item.entity.ItemStorageEnt;
import com.server.user.item.resource.ItemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/13 11:56
 */
@Component
public class ItemManager {

    private static Logger logger = LoggerFactory.getLogger(ItemManager.class);

    @Autowired
    private CommonManager<String, ItemStorageEnt> storageManager;

    @Autowired
    private ResourceManager resourceManager;

    private Map<Integer, ItemResource> itemResources = new HashMap<>();

    public void initItems() {
        ItemResource hpMedicine = ItemResource.valueOf(1001, "生命药水", ItemType.COMMONITEM, 10, 1, 1, 10, "-1");
        ItemResource defMedicine = ItemResource.valueOf(1002, "防御药水", ItemType.COMMONITEM, 10, 1, 1, 10, "-1");
        ItemResource atkMedicine = ItemResource.valueOf(1003, "攻击药水", ItemType.COMMONITEM, 10, 1, 1, 10, "-1");
        ItemResource commonItem1 = ItemResource.valueOf(1004, "一号不可堆叠道具", ItemType.COMMONITEM, 0, 1, 1, 0, "-1");
        ItemResource commonItem2 = ItemResource.valueOf(1005, "一号可堆叠道具", ItemType.COMMONITEM, 10, 1, 1, 0, "-1");
        ItemResource normalClothes = ItemResource.valueOf(2001, "新手衣服", ItemType.EQUIPMENT, EquipmentType.CLOTHES, 1, 1, 1, 0, "-1");
        ItemResource normalWeapon = ItemResource.valueOf(2002, "新手武器", ItemType.EQUIPMENT, EquipmentType.WEAPON, 1, 1, 1, 0, "-1");
        itemResources.put(1001, hpMedicine);
        itemResources.put(1002, defMedicine);
        itemResources.put(1003, atkMedicine);
        itemResources.put(1004, commonItem1);
        itemResources.put(1005, commonItem2);
        itemResources.put(1006, normalClothes);
        itemResources.put(1007, normalWeapon);
    }

    public ItemResource getItemResource(int id) {
        Map<Integer, Object> itemResources = resourceManager.getResources(ItemResource.class.getSimpleName());
        ItemResource resource = (ItemResource) itemResources.get(id);
        if (resource == null) {
            logger.error("找不到对应配置id：{}" + id);
        }
        return resource;
    }


}
