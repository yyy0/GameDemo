package com.server.session.service;

import com.SpringContext;
import com.server.map.model.MapInfo;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import io.netty.channel.Channel;
import io.netty.util.internal.ConcurrentSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author yuxianming
 * @date 2019/5/9 11:15
 */
@Component
public class SessionService {


    /**
     * 在线账户id
     */
    private ConcurrentSet<String> onlineAccounts = new ConcurrentSet<>();

    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    /**
     * 已经登录的session管理
     * string 账号id
     */
    private final ConcurrentMap<String, TSession> idSessionMap = new ConcurrentHashMap<>(16);

    public void createSession(TSession session) {

    }

    public TSession getSession(String accountId) {
        return idSessionMap.get(accountId);
    }

    /**
     * 创建session
     *
     * @param channel 与客户端连接的管道
     * @return session
     */
    public TSession create(Channel channel) {
        TSession session = getSessionByChannel(channel);
        if (session == null) {
//            session = new TSession(channel);
            SessionUtil.addChannelSession(channel);
            logger.info("session 创建成功");
        } else {
            logger.error("新连接建立时已存在Session" + channel.toString());
        }
        return session;
    }

    /**
     * 注册sesson
     *
     * @param session   session
     * @param accountId 账号id
     */
    public void register(TSession session, String accountId) {
        session.regAccount(accountId);
        idSessionMap.put(accountId, session);
    }

    /**
     * 通过channel关闭session
     *
     * @param channel 与客户端连接的管道
     */
    public void close(Channel channel) {
        TSession session = getSessionByChannel(channel);
        if (session != null) {
            close(session);
        }
    }

    /**
     * 关闭session
     *
     * @param session 要关闭的session
     */
    private void close(TSession session) {
        unregister(session);
        session.close();
        logger.info("session close success");
    }

    /**
     * 将之前注册好的session从map中移除
     *
     * @param session 将之前注册好的session从map中移除
     */
    public void unregister(TSession session) {
        if (session != null) {

            String accountId = (String) session.removeAttribute(SessionUtil.ACCOUNT_ID);
            if (accountId != null) {
                Account account = SpringContext.getAccountService().getAccount(accountId);
                MapInfo mapInfo = SpringContext.getWorldService().getMapInfo(account, account.getMapId());
                mapInfo.removeFightAccount(accountId);
                boolean remove = idSessionMap.remove(accountId, session);

                logger.info("Session unregister, accountId={}, remove={}", accountId, remove);
            }
            Channel channel = session.getChannel();
            SessionUtil.removeSession(channel);
            channel.close();
        }
    }


    public TSession getSessionByChannel(Channel channel) {
        return SessionUtil.getSessionByChannel(channel);
    }

    public void sendMessage(Channel channel, String msg) {
        sendMessage(getSessionByChannel(channel), msg);
    }

    public void sendMessage(TSession session, String msg) {
        session.getChannel().writeAndFlush(msg);
    }


}
