package com.server.user.equipment.constant;

/**
 * @author yuxianming
 * @date 2019/5/10 18:30
 */
public enum EquipmentType {
    //武器
    WEAPON(1),
    //衣服
    CLOTHES(2),
    //头盔
    HAT(3),
    //裤子
    BELT(4),
    //鞋子
    SHOES(5),
    //项链
    NECKLACE(6),
    //护腕
    GLOVE(7),
    //翅膀
    WRING(8);

    private int index;

    public static EquipmentType typeOf(int index) {
        for (EquipmentType type : values()) {
            if (type.getIndex() == index) {
                return type;
            }
        }
        throw new RuntimeException("找不到匹配的装备类型！");
    }

    private EquipmentType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
