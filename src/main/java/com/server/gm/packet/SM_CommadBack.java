package com.server.gm.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/16 18:06
 */
public class SM_CommadBack implements Serializable {
    private String back;

    public static SM_CommadBack valueOf(String string) {
        SM_CommadBack backMsg = new SM_CommadBack();
        backMsg.back = string;
        return backMsg;
    }
}
