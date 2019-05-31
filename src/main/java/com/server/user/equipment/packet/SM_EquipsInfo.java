package com.server.user.equipment.packet;

import com.server.user.equipment.constant.EquipmentPosition;
import com.server.user.equipment.model.Equipment;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/31 21:45
 */
public class SM_EquipsInfo implements Serializable {
    private Map<EquipmentPosition, Equipment> equipmentMap;

    public static SM_EquipsInfo valueOf(Map<EquipmentPosition, Equipment> equipmentMap) {
        SM_EquipsInfo packet = new SM_EquipsInfo();
        packet.equipmentMap = equipmentMap;
        return packet;
    }

    public Map<EquipmentPosition, Equipment> getEquipmentMap() {
        return equipmentMap;
    }

    public void setEquipmentMap(Map<EquipmentPosition, Equipment> equipmentMap) {
        this.equipmentMap = equipmentMap;
    }
}
