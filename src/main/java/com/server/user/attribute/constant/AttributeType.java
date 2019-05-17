package com.server.user.attribute.constant;

/**
 * @author yuxianming
 * @date 2019/5/13 18:25
 */
public enum AttributeType {

    /**
     * 生命上限
     */
    MAXHP(1, "生命上限"),
    /**
     * 攻击力
     */
    ATTACK(2, "攻击力"),
    /**
     * 防御力
     */
    DEFENCE(3, "防御力");
    /**
     * 属性id
     */
    private int id;
    /**
     * 属性描述
     */
    private String desc;

    AttributeType(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
