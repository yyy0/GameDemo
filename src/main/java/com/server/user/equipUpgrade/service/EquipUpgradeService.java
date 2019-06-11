package com.server.user.equipUpgrade.service;

import com.SpringContext;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.user.account.model.Account;
import com.server.user.attribute.constant.AttributeModel;
import com.server.user.equipUpgrade.resource.EquipUpgradeResource;
import com.server.user.equipment.constant.EquipmentPosition;
import com.server.user.equipment.model.EquipStorage;
import com.server.user.equipment.model.Equipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/6 10:18
 */
@Component
public class EquipUpgradeService {


    public static Logger logger = LoggerFactory.getLogger(EquipUpgradeService.class);

    /**
     * <部位，<装备阶数，升阶配置>> Map
     */
    private Map<Integer, Map<Integer, EquipUpgradeResource>> upGradeResourceMap;

    /**
     * 预处理装备升阶配置
     */
    public void initEquipUpResource() {
        Map<Integer, Object> upgradeResources = SpringContext.getResourceManager().getResources(EquipUpgradeResource.class.getSimpleName());

        //key 部位  value <key 阶数  value 配置文件>
        Map<Integer, Map<Integer, EquipUpgradeResource>> positionResource = new HashMap<>(EquipmentPosition.values().length);

        for (Object resource : upgradeResources.values()) {
            EquipUpgradeResource equipUpResource = (EquipUpgradeResource) resource;

            //key 阶数  value 配置文件
            Map<Integer, EquipUpgradeResource> upGradeResourceMap = positionResource.get(equipUpResource.getPosition());
            if (upGradeResourceMap == null) {
                upGradeResourceMap = new HashMap<>();
                positionResource.put(equipUpResource.getPosition(), upGradeResourceMap);
            }
            upGradeResourceMap.put(equipUpResource.getLevel(), equipUpResource);

        }
        this.upGradeResourceMap = positionResource;

    }

    /**
     * 装备升阶
     *
     * @param account
     * @param position
     */
    public void equipUpgrade(Account account, int position) {

        // check是否可升阶
        checkUpgradeThrow(account, position);
        EquipStorage storage = SpringContext.getEquipmentService().getEquipStorage(account.getAccountId());
        Equipment equipment = storage.getEquipmentByPosition(position);
        int curLevel = equipment.getGrade();
        EquipUpgradeResource upgradeResource = getEquipUpResource(position, curLevel);
        if (upgradeResource.getNextLevel() == 0) {
            I18Utils.notifyMessage(account, I18nId.EQUIP_GRADE_MAX);
            logger.info("该装备已满阶:" + equipment.getObjectId());
            return;
        }
        Map<Integer, Integer> itemsMap = upgradeResource.getItem();
        for (Map.Entry<Integer, Integer> item : itemsMap.entrySet()) {
            int itemId = item.getKey();
            int num = item.getValue();
            SpringContext.getStoreService().reduceBagItemThrow(account, itemId, num);
        }

        equipment.upGrade(upgradeResource.getNextLevel());
        SpringContext.getEquipmentService().saveEquipStorage(account.getAccountId(), storage);
        SpringContext.getAttributeManager().refreshAttr(account, AttributeModel.EQUIPMENT);
        SpringContext.getEquipmentService().printEquipments(account);

    }

    /**
     * check是否可升阶
     *
     * @param account
     * @param position
     */
    private void checkUpgradeThrow(Account account, int position) {

        EquipStorage equipStorage = SpringContext.getEquipmentService().getEquipStorage(account.getAccountId());
        Equipment equipment = equipStorage.getEquipmentByPosition(EquipmentPosition.typeOf(position));
        if (equipment == null) {
            I18Utils.notifyMessage(account, I18nId.POSITION_NO_EQUIPMENT);
            throw new NullPointerException("该部位没有装备");
        }

        Map<Integer, EquipUpgradeResource> positionResource = upGradeResourceMap.get(position);
        if (positionResource == null) {
            I18Utils.notifyMessage(account, I18nId.EQUIP_POSITION_UPGRADE_LIMIT);
            throw new NullPointerException("该装备部位不可升阶");
        }

        EquipUpgradeResource upgradeResource = positionResource.get(equipment.getGrade());
        if (upgradeResource == null) {
            I18Utils.notifyMessage(account, I18nId.ILLEGAL_VALUE);
            throw new IllegalArgumentException("非法的装备阶数,部位：" + equipment.getEquipmentType() + " 阶数:" + equipment.getGrade());
        }

    }

    /**
     * 根据部位和装备阶数 获取对应配置
     *
     * @param position
     * @param level
     * @return
     */
    public EquipUpgradeResource getEquipUpResource(int position, int level) {
        Map<Integer, EquipUpgradeResource> resourceMap = upGradeResourceMap.get(position);
        if (resourceMap == null) {
            return null;
        }
        return resourceMap.get(level);
    }

    public Map<Integer, Map<Integer, EquipUpgradeResource>> getUpGradeResourceMap() {
        return upGradeResourceMap;
    }

    public void setUpGradeResourceMap(Map<Integer, Map<Integer, EquipUpgradeResource>> upGradeResourceMap) {
        this.upGradeResourceMap = upGradeResourceMap;
    }
}
