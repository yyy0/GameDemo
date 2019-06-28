package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.server.common.constant.SplitSymbol;
import com.server.task.constant.TaskConditionType;
import com.server.task.model.TaskCondition;

/**
 * @author yuxianming
 * @date 2019/6/25 17:27
 */
public class StringToTaskCondition extends AbstractBeanField<TaskCondition> {
    @Override
    protected TaskCondition convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if ("".equals(value)) {
            return null;
        }
        String[] params = value.split(SplitSymbol.SHUXIAN);
        TaskConditionType type = TaskConditionType.valueOf(params[0]);
        TaskCondition condition = new TaskCondition();
        condition.setType(type);
        condition.setParam(params[1]);
        return condition;
    }
}
