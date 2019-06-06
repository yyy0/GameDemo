package com.server.user.attribute.model;

import com.server.user.attribute.constant.AttributeType;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/3 16:31
 */
public class Attribute implements Serializable {

    private AttributeType type;

    private long value;

    public static Attribute valueOf(AttributeType type, long value) {
        Attribute attribute = new Attribute();
        attribute.type = type;
        attribute.value = value;
        return attribute;
    }

    public boolean isRateAttribute() {
        if (type == null) {
            return false;
        }
        return type.isRateAttribute();
    }

    public void addValue(long value) {
        this.value += value;
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

    public String getName() {
        return type.getDesc();
    }
}
