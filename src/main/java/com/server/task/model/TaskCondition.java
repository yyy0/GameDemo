package com.server.task.model;

import com.server.task.constant.TaskConditionType;

/**
 * 任务完成条件
 *
 * @author yuxianming
 * @date 2019/6/25 16:58
 */
public class TaskCondition {

    private TaskConditionType type;

    private String param;

    public TaskConditionType getType() {
        return type;
    }

    public void setType(TaskConditionType type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public boolean isConditionNotEmpty() {
        return param != null && !param.equals("");
    }
}
