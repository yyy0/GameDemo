package com.server.task.facade;

import com.SpringContext;
import com.server.common.event.anno.ReceiverAnno;
import com.server.dispatcher.HandlerAnno;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.task.event.TaskEvent;
import com.server.task.packet.CM_TaskInfo;
import com.server.task.packet.CM_TaskReward;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/25 11:41
 */
@Component
public class TaskFacade {

    /**
     * 打印当前任务信息
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void printTaskInfo(TSession session, CM_TaskInfo req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getTaskService().printTaskInfo(account);
    }

    /**
     * 领取任务奖励
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void rewardTask(TSession session, CM_TaskReward req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getTaskService().rewardFinishTask(account, req.getTaskId());
    }

    /**
     * 处理任务事件
     *
     * @param event
     */
    @ReceiverAnno
    public void handlerTask(TaskEvent event) {
        SpringContext.getTaskService().handlerEvent(event);
    }
}
