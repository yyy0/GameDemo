package com.server.user.account.packet;

import java.io.Serializable;

/**
 * 打印战斗账号信息
 *
 * @author yuxianming
 * @date 2019/6/17 17:57
 */
public class CM_PrintFightAccount implements Serializable {

    public static CM_PrintFightAccount valueOf() {
        CM_PrintFightAccount req = new CM_PrintFightAccount();
        return req;
    }
}
