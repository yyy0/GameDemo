package com.server.user.item.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:09
 * 移动道具从背包到仓库
 */
public class CM_MoveItemToWare implements Serializable {

    /**
     * 道具唯一id
     */
    private int gid;

    public static CM_MoveItemToWare valueOf(int gid) {
        CM_MoveItemToWare packet = new CM_MoveItemToWare();
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
