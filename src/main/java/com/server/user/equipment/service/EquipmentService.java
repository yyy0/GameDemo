package com.server.user.equipment.service;

import com.SpringContext;
import com.server.common.entity.CommonEntManager;
import com.server.common.resource.ResourceManager;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.task.constant.TaskConditionType;
import com.server.task.event.TaskEvent;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;
import com.server.user.attribute.constant.AttributeModel;
import com.server.user.equipment.constant.EquipmentPosition;
import com.server.user.equipment.entity.EquipStorageEnt;
import com.server.user.equipment.model.EquipStorage;
import com.server.user.equipment.model.Equipment;
import com.server.user.equipment.packet.SM_EquipsInfo;
import com.server.user.equipment.resource.EquipResource;
import com.server.user.item.model.AbstractItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/31 14:58
 */
@Component
public class EquipmentService {

    public static Logger logger = LoggerFactory.getLogger(EquipmentService.class);

    @Autowired
    private CommonEntManager<String, EquipStorageEnt> equipManager;

    @Autowired
    private ResourceManager resourceManager;

    private EquipStorageEnt getEquipStorageEnt(String accountId) {
        EquipStorageEnt equipStorageEnt = equipManager.getEnt(EquipStorageEnt.class, accountId);
        if (equipStorageEnt == null) {
            equipStorageEnt = EquipStorageEnt.valueOf(accountId);
            EquipStorage equipStorage = EquipStorage.valueOf();
            equipStorageEnt.setEquipStorage(equipStorage);
            equipStorageEnt.doSerialize(equipStorage);
            equipManager.createEnt(equipStorageEnt);
            equipStorageEnt = equipManager.getEnt(EquipStorageEnt.class, accountId);
        }
        return equipStorageEnt;
    }

    public EquipStorage getEquipStorage(String accountId) {
        EquipStorageEnt equipStorageEnt = getEquipStorageEnt(accountId);
        equipStorageEnt.doDeserialize();
        return equipStorageEnt.getEquipStorage();
    }

    public void saveEquipStorage(String accountId, EquipStorage equipStorage) {
        EquipStorageEnt ent = getEquipStorageEnt(accountId);
        ent.doSerialize(equipStorage);
        equipManager.update();
    }

    public EquipResource getEquipResource(int id) {

        Map<Integer, Object> equipResource = resourceManager.getResources(EquipResource.class.getSimpleName());
        EquipResource resource = (EquipResource) equipResource.get(id);
        if (resource == null) {
            logger.error("EquipResource找不到对应配置id：{0}" + id);
        }
        return resource;
    }

    /**
     * 穿戴装备
     *
     * @param account
     * @param equipId  装备唯一id
     */
    public void equip(Account account, long equipId) {
        AbstractItem item = SpringContext.getStoreService().getItemByObjectId(account.getAccountId(), equipId);
        if (item == null || !item.isEquipment()) {
            I18Utils.notifyMessage(account, I18nId.EQUIPMENT_NOT_EXIST);
            return;
        }

        Equipment equipment = (Equipment) item;
        EquipStorage equipStorage = getEquipStorage(account.getAccountId());
        EquipmentPosition equipmentPosition = EquipmentPosition.typeOf(equipment.getEquipmentType());
        if (!isMatchEquipmentPosition(equipment, equipmentPosition)) {
            I18Utils.notifyMessage(account, I18nId.EQUIPMENT_NOT_MATCH);
            return;
        }
        if (!isCanEquip(account, equipment)) {
            return;
        }
        SpringContext.getStoreService().reduceBagItem(account, item, 1);
        Equipment unEquipment = equipStorage.equip(equipment, equipmentPosition);
        if (unEquipment != null) {
            SpringContext.getStoreService().addItemToBag(account, unEquipment);
        } else {
            //提交穿戴装备事件
            SpringContext.getEventManager().syncSubmit(TaskEvent.valueOf(account, TaskConditionType.EQUIP_NUM, 1));
        }
        saveEquipStorage(account.getAccountId(), equipStorage);
        //刷新属性
        SpringContext.getAttributeManager().refreshAttr(account, AttributeModel.EQUIPMENT);

        printEquipments(account);
    }


    public boolean isCanEquip(Account account, Equipment equipment) {
        if (account.getLevel() < equipment.getEquipLevel()) {
            I18Utils.notifyMessage(account, I18nId.EQUIP_LEVEL_LIMIT);
            return false;
        }
        return true;
    }

    /**
     * 卸下装备
     *
     * @param account
     * @param position 装备部位
     */
    public void unEquip(Account account, int position) {

        EquipmentPosition equipmentPosition = EquipmentPosition.typeOf(position);
        if (equipmentPosition == null) {
            return;
        }
        EquipStorage equipStorage = getEquipStorage(account.getAccountId());
        Equipment equipment = equipStorage.getEquipmentByPosition(equipmentPosition);
        if (equipment == null) {
            I18Utils.notifyMessage(account, I18nId.POSITION_NO_EQUIPMENT);
            return;
        }
        if (!SpringContext.getStoreService().isEnoughPackSize(account)) {
            I18Utils.notifyMessage(account, I18nId.POSITION_NO_EQUIPMENT);
            return;
        }
        equipStorage.unEquip(equipmentPosition);

        SpringContext.getStoreService().addItemToBag(account, equipment);
        //提交脱装备事件
        SpringContext.getEventManager().syncSubmit(TaskEvent.valueOf(account, TaskConditionType.EQUIP_NUM, -1));
        saveEquipStorage(account.getAccountId(), equipStorage);
        SpringContext.getAttributeManager().refreshAttr(account, AttributeModel.EQUIPMENT);
        printEquipments(account);

    }

    /**
     * 装备是否与孔位对应
     *
     * @param equipment
     * @param position
     * @return
     */
    public boolean isMatchEquipmentPosition(Equipment equipment, EquipmentPosition position) {
        return equipment.getEquipmentType() == position.getEquipmentType();
    }

    public void printEquipments(Account account) {
        EquipStorage equipStorage = getEquipStorage(account.getAccountId());
        Map<EquipmentPosition, Equipment> equipmentMap = equipStorage.getEquipments();

        SM_EquipsInfo packet = SM_EquipsInfo.valueOf(equipmentMap);
        PacketSendUtil.send(account, packet);
    }

    public Equipment getEquipmentByPosition(Account account, int position) {
        return getEquipStorage(account.getAccountId()).getEquipmentByPosition(position);
    }
}
