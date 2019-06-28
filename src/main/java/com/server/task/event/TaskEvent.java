package com.server.task.event;

import com.server.common.event.event.IEvent;
import com.server.task.constant.TaskConditionType;
import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/6/25 15:22
 */
public class TaskEvent implements IEvent {

    private Account account;

    @Override
    public long getOwner() {
        return account.getAccountId().hashCode();
    }

    private TaskConditionType type;

    /**
     * 任务所需要的进度值
     */
    private int value;

    public static TaskEvent valueOf(Account account, TaskConditionType type, int value) {
        TaskEvent event = new TaskEvent();
        event.account = account;
        event.type = type;
        event.value = value;
        return event;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TaskConditionType getType() {
        return type;
    }

    public void setType(TaskConditionType type) {
        this.type = type;
    }
}
