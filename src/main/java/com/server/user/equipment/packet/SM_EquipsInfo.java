package com.server.user.equipment.packet;

import com.server.user.equipment.constant.EquipmentPosition;
import com.server.user.equipment.model.Equipment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/31 21:45
 */
public class SM_EquipsInfo implements Serializable {
    private Map<Integer, Equipment> equipmentMap = new HashMap<>();

    public static SM_EquipsInfo valueOf(Map<EquipmentPosition, Equipment> equipmentMap) {
        SM_EquipsInfo packet = new SM_EquipsInfo();
        for (Map.Entry<EquipmentPosition, Equipment> entry : equipmentMap.entrySet()) {
            Equipment equipment = entry.getValue();
            packet.equipmentMap.put(entry.getKey().getId(), equipment);
        }
        return packet;
    }

    public Map<Integer, Equipment> getEquipmentMap() {
        return equipmentMap;
    }

    public void setEquipmentMap(Map<Integer, Equipment> equipmentMap) {
        this.equipmentMap = equipmentMap;
    }
}
