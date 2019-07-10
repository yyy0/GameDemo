package com.server.task.service;

import com.SpringContext;
import com.server.common.entity.CommonEntManager;
import com.server.common.resource.ResourceManager;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.task.entity.TaskEnt;
import com.server.task.event.TaskEvent;
import com.server.task.model.AccountTask;
import com.server.task.model.TaskCondition;
import com.server.task.model.TaskInfo;
import com.server.task.packet.SM_TaskFinish;
import com.server.task.packet.SM_TaskInfo;
import com.server.task.resource.TaskResource;
import com.server.task.taskhandler.AbstractTaskHandler;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;
import com.server.user.item.model.AbstractItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/25 17:54
 */
@Component
public class TaskService {

    public static Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private CommonEntManager<String, TaskEnt> taskEntManager;

    @Autowired
    private ResourceManager resourceManager;

    public TaskEnt getTaskEnt(String accountId) {
        TaskEnt ent = taskEntManager.getEnt(TaskEnt.class, accountId);
        if (ent == null) {
            ent = TaskEnt.valueOf(accountId);
            taskEntManager.createEnt(ent);
        }
        return ent;
    }

    public AccountTask getAccountTask(String accountId) {
        TaskEnt ent = getTaskEnt(accountId);
        ent.doDeserialize();
        return ent.getAccountTask();
    }

    public void saveTaskEnt(String accountId, AccountTask accountTask) {
        TaskEnt ent = getTaskEnt(accountId);
        ent.doSerialize(accountTask);
        taskEntManager.update();
    }

    public TaskResource getTaskResource(int taskId) {
        Map<Integer, Object> equipResource = resourceManager.getResources(TaskResource.class.getSimpleName());
        TaskResource resource = (TaskResource) equipResource.get(taskId);
        if (resource == null) {
            logger.error("TaskResource找不到对应配置id：{0}" + taskId);
        }
        return resource;
    }

    /**
     * 初始化任务 角色登陆时调用
     *
     * @param account
     */
    public void initTask(Account account) {
        AccountTask accountTask = getAccountTask(account.getAccountId());
        Map<Integer, TaskInfo> taskInfoMap = accountTask.getProcessingTask();
        if (taskInfoMap.size() == 0) {
            return;
        }
        for (TaskInfo taskInfo : taskInfoMap.values()) {
        }
    }


    public void handlerEvent(TaskEvent event) {
        AbstractTaskHandler handler = AbstractTaskHandler.getHandler(event.getType());
        Account account = event.getAccount();
        AccountTask accountTask = getAccountTask(account.getAccountId());
        Map<Integer, TaskInfo> processTasks = accountTask.getProcessingTask();
        //判断是否有进行中的任务
        if (processTasks.size() == 0) {
            return;
        }

        // 遍历进行中的任务
        for (TaskInfo info : processTasks.values()) {
            TaskResource resource = getTaskResource(info.getTaskId());
            if (resource == null) {
                logger.error("找不到TaskResource：" + info.getTaskId());
                continue;
            }
            if (resource.getConditionType() == event.getType()) {

                //刷新任务进度
                handler.refreshTaskProgress(resource.getCondition(), event, info);

                doAfterRefreshProgress(account, info, resource);
            }
        }

        saveTaskEnt(account.getAccountId(), accountTask);

    }

    public void doAfterRefreshProgress(Account account, TaskInfo taskInfo, TaskResource resource) {

        if (canFinishTask(taskInfo, resource)) {
            SM_TaskFinish packet = SM_TaskFinish.valueOf(taskInfo.getTaskId());
            PacketSendUtil.send(account, packet);
        }
    }


    /**
     * 完成任务领取奖励
     *
     * @param account
     * @param taskId
     */
    public void rewardFinishTask(Account account, int taskId) {
        //check配置
        TaskResource resource = getTaskResource(taskId);
        if (resource == null) {
            logger.error("任务resource找不到： " + taskId);
            return;
        }

        //判断进行中的任务是否有该任务
        AccountTask accountTask = getAccountTask(account.getAccountId());
        if (!accountTask.getProcessingTask().containsKey(taskId)) {
            I18Utils.notifyMessageThrow(account, I18nId.TASK_NOT_FINISH);
        }

        //判断是否能完成任务
        TaskInfo taskInfo = accountTask.getTaskFromProcess(taskId);
        if (!canFinishTask(taskInfo, resource)) {
            I18Utils.notifyMessageThrow(account, I18nId.TASK_NOT_FINISH);
        }

        // 判断背包是否足够 足够则发奖
        Map<Integer, Integer> reward = resource.getReward();
        List<AbstractItem> items = SpringContext.getStoreService().createItems(reward);
        if (!SpringContext.getStoreService().isEnoughPackSize(account, items)) {
            I18Utils.notifyMessageThrow(account, I18nId.BAG_NOT_ENOUGH);
        }
        SpringContext.getStoreService().addItemsToBag(account, items);

        //通知领取任务成功
        I18Utils.notifyMessage(account, I18nId.TASK_REWARD_SUCCESS);

        //设置任务已完成
        accountTask.removeTaskFromProcess(taskId);
        accountTask.addFinishTask(taskId);


        //判断是否有下个任务
        if (resource.getNextTask() == 0) {
            I18Utils.notifyMessage(account, I18nId.FINISH_ALL_TASK);
        } else {
            addNewTask(account, resource.getNextTask(), accountTask);
        }
        saveTaskEnt(account.getAccountId(), accountTask);

        //发送打印最新的任务信息
        SM_TaskInfo packet = SM_TaskInfo.valueOf(accountTask);
        PacketSendUtil.send(account, packet);
    }

    private boolean canFinishTask(TaskInfo info, TaskResource resource) {

        int finishValue = Integer.parseInt(resource.getCondition().getParam());
        if (info.getProgress() < finishValue) {
            return false;
        } else {
            info.setProgress(finishValue);
        }
        return true;

    }

    private void addNewTask(Account account, int taskId, AccountTask accountTask) {
        TaskResource resource = getTaskResource(taskId);
        if (resource == null) {
            logger.error("找不到TaskResource: " + taskId);
            return;
        }

        TaskInfo taskInfo = TaskInfo.valueOf(taskId);
        initTaskProgress(resource, taskInfo, account);
        // 判断是否已完成 完成则通知客户端
        if (canFinishTask(taskInfo, resource)) {
            SM_TaskFinish packet = SM_TaskFinish.valueOf(taskInfo.getTaskId());
            PacketSendUtil.send(account, packet);
        }
        accountTask.addProcessTask(taskInfo);

    }


    /**
     * 初始化任务进度
     *
     * @param resource
     * @param taskInfo
     * @param account
     */
    public void initTaskProgress(TaskResource resource, TaskInfo taskInfo, Account account) {

        TaskCondition condition = resource.getCondition();

        if (condition.isConditionNotEmpty()) {

            AbstractTaskHandler taskHandler = AbstractTaskHandler.getHandler(condition.getType());
            taskHandler.initTaskProgress(taskInfo, account);
        }
    }

    /**
     * 打印玩家当前进行中的任务信息
     *
     * @param account
     */
    public void printTaskInfo(Account account) {
        AccountTask accountTask = getAccountTask(account.getAccountId());
        SM_TaskInfo packet = SM_TaskInfo.valueOf(accountTask);
        PacketSendUtil.send(account, packet);
    }

}
