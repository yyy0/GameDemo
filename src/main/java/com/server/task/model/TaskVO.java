package com.server.task.model;

import java.io.Serializable;

/**
 * 任务信息VO
 *
 * @author yuxianming
 * @date 2019/6/27 20:40
 */
public class TaskVO implements Serializable {

    private Integer taskId;

    private Integer progress;

    private Integer finalValue;

    public static TaskVO valueOf(TaskInfo info, TaskCondition condition) {
        TaskVO taskVO = new TaskVO();
        taskVO.taskId = info.getTaskId();
        taskVO.progress = info.getProgress();
        taskVO.finalValue = Integer.parseInt(condition.getParam());
        return taskVO;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(Integer finalValue) {
        this.finalValue = finalValue;
    }
}
