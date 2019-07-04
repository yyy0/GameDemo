package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;
import org.springframework.util.NumberUtils;

/**
 * @author yuxianming
 * @date 2019/5/23 1:12
 */
public class StringToIntegerConverter<T extends Number> extends AbstractBeanField<T> {


    @Override
    protected Object convert(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

//        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return NumberUtils.parseNumber(s, Integer.class);
    }

}
