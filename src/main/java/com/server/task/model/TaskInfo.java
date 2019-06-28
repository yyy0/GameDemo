package com.server.task.model;

/**
 * 单个任务信息
 *
 * @author yuxianming
 * @date 2019/6/26 11:19
 */
public class TaskInfo {

    private int taskId;

    /**
     * 任务进度
     */
    private int progress;

    public static TaskInfo valueOf(int taskId) {
        TaskInfo info = new TaskInfo();
        info.taskId = taskId;
        return info;
    }

    /**
     * 修改任务的值
     *
     * @param value
     * @param isReplace
     */
    public void changeProgress(int value, boolean isReplace) {

        if (isReplace) {
            this.progress = value;
        } else {
            this.progress += value;
        }
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
