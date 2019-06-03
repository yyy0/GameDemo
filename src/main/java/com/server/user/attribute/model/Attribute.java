package com.server.user.attribute.model;

import com.server.user.attribute.constant.AttributeType;

/**
 * @author yuxianming
 * @date 2019/6/3 16:31
 */
public class Attribute {

    private AttributeType type;

    private long value;

    public static Attribute valueOf(AttributeType type, long value) {
        Attribute attribute = new Attribute();
        attribute.type = type;
        attribute.value = value;
        return attribute;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
