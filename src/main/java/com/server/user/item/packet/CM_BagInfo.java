package com.server.user.item.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:06
 */
public class CM_BagInfo implements Serializable {

    public static CM_BagInfo valueOf() {
        CM_BagInfo packet = new CM_BagInfo();
        return packet;
    }
}
