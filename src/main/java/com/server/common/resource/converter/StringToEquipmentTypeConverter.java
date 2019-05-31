package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.server.user.equipment.constant.EquipmentType;

/**
 * @author yuxianming
 * @date 2019/5/27 15:09
 */
public class StringToEquipmentTypeConverter extends AbstractBeanField<EquipmentType> {
    @Override
    protected EquipmentType convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if ("".equals(value)) {
            return null;
        }
        return EquipmentType.valueOf(value);
    }
}
