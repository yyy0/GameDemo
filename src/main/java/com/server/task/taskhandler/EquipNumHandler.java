package com.server.task.taskhandler;

import com.server.task.constant.TaskConditionType;
import com.server.task.event.TaskEvent;
import com.server.task.model.TaskCondition;
import com.server.task.model.TaskInfo;
import com.server.task.resource.TaskResource;
import com.server.user.account.model.Account;
import com.server.user.equipment.model.EquipStorage;
import org.springframework.stereotype.Component;

/**
 * 穿戴x件装备  任务事件处理器
 *
 * @author yuxianming
 * @date 2019/6/25 18:12
 */
@Component
public class EquipNumHandler extends AbstractTaskHandler {


    @Override
    public TaskConditionType getTaskConditionType() {
        return TaskConditionType.EQUIP_NUM;
    }

    @Override
    public int getValue(TaskEvent event, TaskCondition condition) {


        return getEquipNum(event.getAccount());
    }

    @Override
    public boolean isReplace() {
        return false;
    }


    /**
     * 初始化任务进度
     *
     * @param taskInfo
     * @param account
     */
    @Override
    public void initTaskProgress(TaskInfo taskInfo, Account account) {

        TaskResource resource = getTaskResource(taskInfo.getTaskId());
        TaskCondition condition = resource.getCondition();
        int conditionValue = Integer.parseInt(condition.getParam());

        int value = getEquipNum(account);
        if (value > 0) {
            taskInfo.setProgress(Math.min(value, conditionValue));
        }

    }

    public int getEquipNum(Account account) {
        EquipStorage equipStorage = account.getEquipStorage();
        return equipStorage.getEquipmentNum();
    }


}
