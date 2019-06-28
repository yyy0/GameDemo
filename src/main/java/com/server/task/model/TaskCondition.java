package com.server.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private transient String[] params;

    @JsonIgnore
    public String[] getParams() {
        if (params == null) {
            this.params = param.split(":");
        }
        return this.params;
    }

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

    public void setParams(String[] params) {
        this.params = params;
    }

    public boolean isConditionNotEmpty() {
        return param != null && !param.equals("");
    }
}
