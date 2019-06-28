package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.server.common.constant.SplitSymbol;
import com.server.tool.JsonUtils;

/**
 * @author yuxianming
 * @date 2019/5/27 15:54
 */
public class StringToIntMatrixConverter extends AbstractBeanField<int[][]> {
    @Override
    protected int[][] convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if ("".equals(value)) {
            return null;
        }
        String newValue = value.replace(SplitSymbol.FENHAO, SplitSymbol.DOUHAO);
        return JsonUtils.json2pojo(newValue, int[][].class);
    }
}
