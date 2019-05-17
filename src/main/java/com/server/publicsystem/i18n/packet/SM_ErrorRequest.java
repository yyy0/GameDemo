package com.server.publicsystem.i18n.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/17 17:40
 */
public class SM_ErrorRequest implements Serializable {
    String req;

    public static SM_ErrorRequest valueOf(String req) {
        SM_ErrorRequest packet = new SM_ErrorRequest();
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
