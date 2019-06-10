package com.server.user.item.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 10:58
 */
public class CM_ClearBag implements Serializable {

    public static CM_ClearBag valueOf() {
        CM_ClearBag packet = new CM_ClearBag();
        return packet;
    }
}
