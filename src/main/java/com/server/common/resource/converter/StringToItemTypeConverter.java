package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;
import com.server.user.item.constant.ItemType;

/**
 * @author yuxianming
 * @date 2019/5/27 15:02
 */
public class StringToItemTypeConverter extends AbstractBeanField<ItemType> {
    @Override
    protected ItemType convert(String value) {
        if ("".equals(value)) {
            return null;
        }
        return ItemType.valueOf(value);
    }
}
