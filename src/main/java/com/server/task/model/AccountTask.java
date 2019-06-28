package com.server.task.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色所有任务信息
 *
 * @author yuxianming
 * @date 2019/6/25 21:14
 */
public class AccountTask {


    /**
     * 已完成的任务id
     */
    private List<Integer> finishTask = new ArrayList<>();

    /**
     * 正在执行的任务
     */
    private Map<Integer, TaskInfo> processingTask = new HashMap<>();

    public List<Integer> getFinishTask() {
        return finishTask;
    }

    public void setFinishTask(List<Integer> finishTask) {
        this.finishTask = finishTask;
    }

    public Map<Integer, TaskInfo> getProcessingTask() {
        return processingTask;
    }

    public void setProcessingTask(Map<Integer, TaskInfo> processingTask) {
        this.processingTask = processingTask;
    }

    public void addProcessTask(TaskInfo taskInfo) {
        processingTask.put(taskInfo.getTaskId(), taskInfo);
    }

    public void addFinishTask(int taskId) {
        finishTask.add(taskId);
    }

    public TaskInfo getTaskFromProcess(int taskId) {
        return processingTask.get(taskId);
    }

    public void removeTaskFromProcess(int taskId) {
        processingTask.remove(taskId);
    }
}
