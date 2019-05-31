package com.server.user.equipment.constant;

/**
 * @author yuxianming
 * @date 2019/5/31 14:45
 */
public enum EquipmentPosition {
    //武器
    WEAPON(1, EquipmentType.WEAPON),
    //衣服
    CLOTHES(2, EquipmentType.CLOTHES),
    //头盔
    HAT(3, EquipmentType.HAT),
    //裤子
    BELT(4, EquipmentType.BELT),
    //鞋子
    SHOES(5, EquipmentType.SHOES),
    //项链
    NECKLACE(6, EquipmentType.NECKLACE),
    //护腕
    GLOVE(7, EquipmentType.GLOVE),
    //翅膀
    WRING(8, EquipmentType.WRING);

    private int id;
    private EquipmentType equipmentType;

    private EquipmentPosition(int id, EquipmentType equipmentType) {
        this.id = id;
        this.equipmentType = equipmentType;
    }

    public static EquipmentPosition typeOf(int id) {
        for (EquipmentPosition equipmentPosition : EquipmentPosition.values()) {
            if (equipmentPosition.getId() == id) {
                return equipmentPosition;
            }
        }
        throw new NullPointerException("找不到匹配的装备部位！");
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }
}
