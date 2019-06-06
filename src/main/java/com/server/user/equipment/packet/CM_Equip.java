package com.server.user.equipment.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:19
 * 穿戴装备
 */
public class CM_Equip implements Serializable {

    /**
     * 道具唯一id
     */
    private int gid;

    public static CM_Equip valueOf(int gid) {
        CM_Equip packet = new CM_Equip();
        packet.gid = gid;
        return packet;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
