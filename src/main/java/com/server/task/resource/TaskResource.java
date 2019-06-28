package com.server.task.resource;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.server.common.resource.converter.StringToIntegerConverter;
import com.server.common.resource.converter.StringToIntegerMap;
import com.server.common.resource.converter.StringToTaskCondition;
import com.server.common.resource.reader.ResId;
import com.server.common.resource.reader.Resource;
import com.server.task.constant.TaskConditionType;
import com.server.task.model.TaskCondition;

import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/25 11:42
 */
@Resource
public class TaskResource {

    @ResId
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int id;

    @CsvBindByName
    private String name;

    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int nextTask;

    @CsvCustomBindByName(converter = StringToTaskCondition.class)
    private TaskCondition condition;

    /**
     * 掉落道具  key：道具id  value：道具数量
     */
    @CsvCustomBindByName(converter = StringToIntegerMap.class)
    private Map<Integer, Integer> reward;


    public int getId() {
        return id;
    }


    public TaskConditionType getConditionType() {
        return condition.getType();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNextTask() {
        return nextTask;
    }

    public void setNextTask(int nextTask) {
        this.nextTask = nextTask;
    }

    public TaskCondition getCondition() {
        return condition;
    }

    public Map<Integer, Integer> getReward() {
        return reward;
    }

    public void setReward(Map<Integer, Integer> reward) {
        this.reward = reward;
    }

    public void setCondition(TaskCondition condition) {
        this.condition = condition;
    }
}
