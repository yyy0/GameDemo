package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;
import com.server.map.constant.MapType;

/**
 * @author yuxianming
 * @date 2019/7/1 21:17
 */
public class StringToMapTypeConverter extends AbstractBeanField<MapType> {
    @Override
    protected MapType convert(String value) {

        if (value == null || "".equals(value)) {
            return null;
        }
        return MapType.valueOf(value);
    }
}
