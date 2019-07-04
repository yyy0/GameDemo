package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;

/**
 * @author yuxianming
 * @date 2019/7/1 12:26
 */
public class StringToLongConverter extends AbstractBeanField<Long> {
    @Override
    protected Long convert(String s) {
        if (s == null || s.length() == 0) {
            return 0L;
        }

        return Long.parseLong(s);
    }
}
