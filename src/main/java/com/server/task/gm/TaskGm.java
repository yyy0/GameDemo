package com.server.task.gm;

import com.SpringContext;
import com.server.gm.anno.GmAnno;
import com.server.gm.anno.GmMethod;
import com.server.task.packet.CM_TaskInfo;
import com.server.task.packet.CM_TaskReward;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/25 18:03
 */
@Component
@GmAnno(title = "任务gm")
public class TaskGm {

    @GmMethod(name = "打印当前任务信息", clz = CM_TaskInfo.class)
    public void printTaskInfo(Account account) {
        SpringContext.getTaskService().printTaskInfo(account);
    }

    @GmMethod(name = "领任务奖励", param = "参数:任务id", clz = CM_TaskReward.class)
    public void rewardTask(Account account, int taskId) {
        SpringContext.getTaskService().rewardFinishTask(account, taskId);
    }
}
