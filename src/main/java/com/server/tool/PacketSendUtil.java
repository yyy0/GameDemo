package com.server.tool;

import com.SpringContext;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/5/16 16:15
 */
public class PacketSendUtil {

    public static final void send(Account account, Object packet) {
        TSession session = SpringContext.getSessionService().getSession(account.getAccountId());
        if (session != null) {
            send(session, packet);
        }
    }

    public static void send(TSession session, Object packet) {
        if (session != null) {
            System.out.println("发送back消息：" + packet.getClass().getSimpleName());
            session.sendPacket(packet);
        }
    }
}
