package com.server.user.item.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:12
 */
public class CM_MoveItemWareToBag implements Serializable {

    private int gid;

    public static CM_MoveItemWareToBag valueOf(int gid) {
        CM_MoveItemWareToBag packet = new CM_MoveItemWareToBag();
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
