package com.server.task.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/27 18:25
 */
public class SM_TaskFinish implements Serializable {

    private int taskId;

    public static SM_TaskFinish valueOf(int taskId) {
        SM_TaskFinish packet = new SM_TaskFinish();
        packet.taskId = taskId;
        return packet;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
