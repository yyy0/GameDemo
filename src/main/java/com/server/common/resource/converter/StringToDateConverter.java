package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yuxianming
 * @date 2019/5/23 1:11
 */
public class StringToDateConverter extends AbstractBeanField<Date> {
    @Override
    public Date convert(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            throw new IllegalArgumentException("字符串[" + s + "]不符合格式要求[yyyy-MM-dd HH:MM:ss]", e);
        }
    }
}
