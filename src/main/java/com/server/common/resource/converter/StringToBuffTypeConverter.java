package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;
import com.server.user.buff.constant.BuffType;

/**
 * @author yuxianming
 * @date 2019/6/18 15:05
 */
public class StringToBuffTypeConverter extends AbstractBeanField<BuffType> {
    @Override
    protected BuffType convert(String value) {

        if ("".equals(value)) {
            return null;
        }

        int buffId = Integer.parseInt(value);

        return BuffType.typeOf(buffId);
    }
}
