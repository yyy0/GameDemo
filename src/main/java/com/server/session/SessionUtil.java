package com.server.session;

import com.SpringContext;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @author yuxianming
 * @date 2019/5/8 18:27
 */
public class SessionUtil {

    public static final AttributeKey<TSession> SESSION = AttributeKey.valueOf("session");
    public static final String ACCOUNT_ID = "ACCOUNT_ID";

    /**
     * 添加新的session
     *
     * @param channel
     * @return
     */
    public static boolean addChannelSession(Channel channel) {
        Attribute<TSession> sessionAttr = channel.attr(SESSION);
        return sessionAttr.compareAndSet(null, new TSession(channel));
    }

    public static TSession getSessionByChannel(Channel channel) {
        Attribute<TSession> sessionAttr = channel.attr(SESSION);
        return sessionAttr.get();
    }

    public static void removeSession(Channel channel) {
        channel.attr(SessionUtil.SESSION).set(null);
    }

    public static String getIp(Channel channel) {
        return ((InetSocketAddress) channel.remoteAddress()).getAddress().toString().substring(1);

    }

    /**
     * 添加账户id至session
     *
     * @param session
     * @param accountId
     */
    public static void addAccountIdToSession(TSession session, String accountId) {
        session.setAttribute(SessionUtil.ACCOUNT_ID, accountId);
    }

    public static String getAccountIdBySession(TSession session) {
        Object accountId = session.getAttribute(SessionUtil.ACCOUNT_ID);
        if (accountId != null) {
            return (String) accountId;
        }
        return null;
    }

    public static Account getAccountBySession(TSession session) {
        String accountId = getAccountIdBySession(session);
        Account account = SpringContext.getAccountService().getAccount(accountId);
        return account;
    }

}
