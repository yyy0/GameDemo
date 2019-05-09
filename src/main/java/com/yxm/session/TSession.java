package com.yxm.session;

import com.yxm.user.account.Account;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yuxianming
 * @date 2019/5/7 21:29
 */
public class TSession {
    private static final Logger logger = LoggerFactory.getLogger(TSession.class);
    private final Channel channel;
    private final long createTime;
    private Account account;

    public TSession(Channel channel) {
        this.channel = channel;
        this.createTime = System.currentTimeMillis();
    }

    /**
     * 放入账号
     *
     * @param account
     */
    public void regAccount(Account account) {
        this.account = account;
    }

    /**
     * 与客户端关闭连接
     */
    public void close() {
        if (channel == null) {
            return;
        }
        try {
            if (channel.isActive() || channel.isOpen()) {
                channel.close().sync();
            }
        } catch (InterruptedException e) {
            logger.error("channel.close find error ", e);
        }

    }

    public Channel getChannel() {
        return channel;
    }

    public Account getAccount() {
        return account;
    }
}
