package com.server.task.packet;

import java.io.Serializable;

/**
 * 领取任务奖励
 *
 * @author yuxianming
 * @date 2019/6/27 15:51
 */
public class CM_TaskReward implements Serializable {

    private int taskId;

    public static CM_TaskReward valueOf(int taskId) {
        CM_TaskReward packet = new CM_TaskReward();
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
