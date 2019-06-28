package com.server.task.taskhandler;

import com.server.common.resource.ResourceManager;
import com.server.task.constant.TaskConditionType;
import com.server.task.event.TaskEvent;
import com.server.task.model.TaskCondition;
import com.server.task.model.TaskInfo;
import com.server.task.resource.TaskResource;
import com.server.user.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/25 15:54
 */
public abstract class AbstractTaskHandler {

    @Autowired
    private ResourceManager resourceManager;

    /**
     * 获取事件完成条件类型
     *
     * @return
     */
    public abstract TaskConditionType getTaskConditionType();

    private static final Map<TaskConditionType, AbstractTaskHandler> handlerMap = new HashMap<>();

    @PostConstruct
    private void init() {
        handlerMap.put(getTaskConditionType(), this);
    }

    /**
     * 任务进度的值
     *
     * @param event
     * @param condition
     * @return
     */
    public abstract int getValue(TaskEvent event, TaskCondition condition);

    public TaskResource getTaskResource(int taskId) {
        Map<Integer, Object> taskResources = resourceManager.getResources(TaskResource.class.getSimpleName());
        if (taskResources != null) {
            return (TaskResource) taskResources.get(taskId);
        }
        return null;
    }

    /**
     * 是否替换进度的值， true为替换 false 为不替换 在原有的进度累加
     *
     * @return
     */
    public abstract boolean isReplace();

    /**
     * 刷新任务进度
     *
     * @param condition
     * @param event
     * @return
     */
    public void refreshTaskProgress(TaskCondition condition, TaskEvent event, TaskInfo info) {

        if (condition.getType() != event.getType()) {
            return;
        }

        int value = event.getValue();
        info.changeProgress(value, isReplace());

    }

    /**
     * 初始化任务进度
     *
     * @param taskInfo
     * @param account
     */
    public abstract void initTaskProgress(TaskInfo taskInfo, Account account);

    /**
     * 根据任务类型获取对应的处理器
     *
     * @param type
     * @return
     */
    public static AbstractTaskHandler getHandler(TaskConditionType type) {
        return handlerMap.get(type);
    }


}
