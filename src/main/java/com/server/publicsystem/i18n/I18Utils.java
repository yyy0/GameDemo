package com.server.publicsystem.i18n;

import com.server.publicsystem.i18n.packet.SM_Notify_Message;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/5/31 17:14
 */
public class I18Utils {


    public static void notifyMessage(Account account, int i18Id) {
        SM_Notify_Message packet = SM_Notify_Message.valueOf(i18Id);
        PacketSendUtil.send(account, packet);
    }
}
