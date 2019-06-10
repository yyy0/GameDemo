package com.server.user.item.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:03
 */
public class CM_ClearStorage implements Serializable {

    public static CM_ClearStorage valueOf() {
        CM_ClearStorage packet = new CM_ClearStorage();
        return packet;
    }
}
