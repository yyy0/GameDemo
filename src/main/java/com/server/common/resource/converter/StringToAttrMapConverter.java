package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.Attribute;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/3 21:37
 * 属性配置转换器
 */
public class StringToAttrMapConverter extends AbstractBeanField {
    @Override
    protected Map<AttributeType, Attribute> convert(String value) {
        if (value == null || "".equals(value)) {
            return null;
        }
        String[] attributeArray = value.split(";");
        Map<AttributeType, Attribute> attrMap = new HashMap<>();
        for (String attributeStr : attributeArray) {
            String[] split = attributeStr.split("_");
            int attributeId = Integer.parseInt(split[0]);
            long attributeValue = Long.parseLong(split[1]);
            AttributeType type = AttributeType.typeOf(attributeId);
            Attribute attribute = Attribute.valueOf(type, attributeValue);
            attrMap.put(type, attribute);
        }

        return attrMap;
    }
}
