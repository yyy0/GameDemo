package com.server.map.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/7/3 14:58
 */
public class SM_NotifyLeaveMap implements Serializable {

    private long countDown;

    private int leaveMapId;

    public static SM_NotifyLeaveMap valueOf(long countDown, int leaveMapId) {
        SM_NotifyLeaveMap packet = new SM_NotifyLeaveMap();
        packet.countDown = countDown;
        packet.leaveMapId = leaveMapId;
        return packet;
    }

    public long getCountDown() {
        return countDown;
    }

    public void setCountDown(long countDown) {
        this.countDown = countDown;
    }

    public int getLeaveMapId() {
        return leaveMapId;
    }

    public void setLeaveMapId(int leaveMapId) {
        this.leaveMapId = leaveMapId;
    }
}
