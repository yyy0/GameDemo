package com.server.user.attribute;

import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.Attribute;

import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/4 18:04
 */
public class AttributeUtil {

    /**
     * 将两个属性map累加
     * 第一个为源数据 第二为结果
     *
     * @param attributeMap
     */
    public static void computeAttribute(Map<AttributeType, Attribute> attributeMap,
                                        Map<AttributeType, Attribute> result) {
        if (attributeMap == null) {
            return;
        }
        for (Attribute attr : attributeMap.values()) {
            long addValue = attr.getValue();
            if (addValue == 0) {
                continue;
            }
            Attribute attribute = result.get(attr.getType());
            if (attribute != null) {
                attribute.addValue(addValue);
            } else {
                result.put(attr.getType(), Attribute.valueOf(attr.getType(), addValue));
            }
        }

    }


    /**
     * 累加属性的基础值
     *
     * @param attributes
     * @param type
     * @return
     */
    public static long getAttributeValue(Map<AttributeType, Attribute> attributes, AttributeType type) {
        long value = 0;
        if (attributes.get(type) != null) {
            value += attributes.get(type).getValue();
        }
        return value > 0 ? value : 0;
    }

    /**
     * 累加属性的百分比值
     *
     * @param attributes
     * @param type
     * @return
     */
    public static long getAttributeRate(Map<AttributeType, Attribute> attributes, AttributeType type) {
        long rate = 0;
        if (type.getCalculateRateAttribute() == null) {
            return rate;
        }
        for (AttributeType temp : type.getCalculateRateAttribute()) {
            if (attributes.get(temp) != null) {
                rate += attributes.get(temp).getValue();
            }
        }
        return rate > 0 ? rate : 0;
    }
}
