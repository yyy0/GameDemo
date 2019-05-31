package com.server.user.equipment.model;

import com.server.user.equipment.constant.EquipmentPosition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/31 11:25
 */
public class EquipStorage {

    /**
     * key：装备部位  value：装备
     */
    private Map<EquipmentPosition, Equipment> equipments = new HashMap<>();

    public static EquipStorage valueOf() {
        EquipStorage equipStorage = new EquipStorage();
        for (EquipmentPosition position : EquipmentPosition.values()) {
            equipStorage.equipments.put(position, null);
        }
        return equipStorage;
    }

    public Equipment getEquipmentByPosition(EquipmentPosition position) {
        return equipments.get(position);
    }

    public Equipment equip(Equipment equipment, EquipmentPosition position) {
        return equipments.put(position, equipment);
    }

    public void unEquip(EquipmentPosition position) {
        equipments.put(position, null);
    }

    public void printEquips() {
        for (Map.Entry<EquipmentPosition, Equipment> entry : equipments.entrySet()) {
            System.out.println(entry.getKey().name() + ":" + entry.getValue());
        }
    }

    public Map<EquipmentPosition, Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(Map<EquipmentPosition, Equipment> equipments) {
        this.equipments = equipments;
    }
}
