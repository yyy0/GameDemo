package com.server.user.account.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/21 22:41
 */
public class SM_RegFail implements Serializable {
    String req;

    public static SM_RegFail valueOf(String req) {
        SM_RegFail packet = new SM_RegFail();
        packet.req = req;
        return packet;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }
}
