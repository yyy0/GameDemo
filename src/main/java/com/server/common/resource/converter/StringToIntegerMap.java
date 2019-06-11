package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/6 16:57
 * map配置转换器  格式：key_value;key_value;key_value
 *
 */
public class StringToIntegerMap extends AbstractBeanField<Map<Integer, Integer>> {
    @Override
    protected Map<Integer, Integer> convert(String value) {
        if (value == null || "".equals(value)) {
            return null;
        }
        String[] itemArray = value.split(";");
        Map<Integer, Integer> items = new HashMap<>(itemArray.length);
        for (String itemStr : itemArray) {
            String[] split = itemStr.split("_");
            int itemId = Integer.parseInt(split[0]);
            int num = Integer.parseInt(split[1]);
            items.put(itemId, num);
        }
        return items;
    }
}
