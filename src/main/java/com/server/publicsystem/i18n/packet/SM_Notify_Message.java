package com.server.publicsystem.i18n.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/17 17:40
 */
public class SM_Notify_Message implements Serializable {
    int I18Id;

    public static SM_Notify_Message valueOf(int I18Id) {
        SM_Notify_Message packet = new SM_Notify_Message();
        packet.I18Id = I18Id;
        return packet;
    }

    public int getI18Id() {
        return I18Id;
    }

    public void setI18Id(int i18Id) {
        this.I18Id = i18Id;
    }
}
