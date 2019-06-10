package com.server.user.account.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/10 15:27
 */
public class CM_PrintAccount implements Serializable {

    public static CM_PrintAccount valueOf() {
        CM_PrintAccount req = new CM_PrintAccount();
        return req;
    }

}
