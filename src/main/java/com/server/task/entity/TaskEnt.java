package com.server.task.entity;

import com.server.task.model.AccountTask;
import com.server.task.model.TaskInfo;
import com.server.tool.JsonUtils;

import javax.persistence.*;

/**
 * @author yuxianming
 * @date 2019/6/25 22:02
 */
@Entity
@Table(name = "task")
public class TaskEnt {

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号名称'", nullable = false)
    private String accountId;

    @Transient
    private AccountTask accountTask;

    @Lob
    @Column(columnDefinition = "blob comment '账号任务数据'")
    private byte[] taskData;

    public static TaskEnt valueOf(String accountId) {
        TaskEnt ent = new TaskEnt();
        ent.accountId = accountId;
        AccountTask info = new AccountTask();
        info.addProcessTask(TaskInfo.valueOf(1001));
        ent.setAccountTask(info);
        ent.doSerialize();
        return ent;
    }

    public void doSerialize(AccountTask accountTask) {
        this.taskData = JsonUtils.objToByte(accountTask);
    }

    public void doSerialize() {
        this.taskData = JsonUtils.objToByte(accountTask);
    }

    public void doDeserialize() {
        this.accountTask = JsonUtils.byteToObj(taskData, AccountTask.class);

    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public AccountTask getAccountTask() {
        return accountTask;
    }

    public void setAccountTask(AccountTask accountTask) {
        this.accountTask = accountTask;
    }

    public byte[] getTaskData() {
        return taskData;
    }

    public void setTaskData(byte[] taskData) {
        this.taskData = taskData;
    }
}
