package com.server.task.packet;

import com.SpringContext;
import com.server.task.model.AccountTask;
import com.server.task.model.TaskInfo;
import com.server.task.model.TaskVO;
import com.server.task.resource.TaskResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 角色当前进行中的任务
 *
 * @author yuxianming
 * @date 2019/6/27 20:46
 */
public class SM_TaskInfo implements Serializable {

    private List<TaskVO> list;

    public static SM_TaskInfo valueOf(AccountTask accountTask) {
        SM_TaskInfo packet = new SM_TaskInfo();
        Map<Integer, TaskInfo> taskInfoMap = accountTask.getProcessingTask();
        packet.list = new ArrayList<>(taskInfoMap.size());
        for (TaskInfo info : taskInfoMap.values()) {
            TaskResource resource = SpringContext.getTaskService().getTaskResource(info.getTaskId());
            if (resource == null) {
                continue;
            }
            TaskVO taskVO = TaskVO.valueOf(info, resource.getCondition());
            packet.list.add(taskVO);
        }

        return packet;
    }

    public List<TaskVO> getList() {
        return list;
    }

    public void setList(List<TaskVO> list) {
        this.list = list;
    }
}
