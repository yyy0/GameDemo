package com.server.session.model;

import com.server.server.message.MessageContent;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuxianming
 * @date 2019/5/7 21:29
 */
public class TSession {
    private static final Logger logger = LoggerFactory.getLogger(TSession.class);
    private final Channel channel;
    private final long createTime;

    /**
     * 账号id
     */
    private String accountId;

    /**
     * 指定session的attributes 暂时只有一种key accountId
     */
    private ConcurrentHashMap<String, Object> attributes = new ConcurrentHashMap<>();

    public TSession(Channel channel) {
        this.channel = channel;
        this.createTime = System.currentTimeMillis();
    }

    /**
     * 注册账号id
     *
     * @param accountId
     */
    public void regAccount(String accountId) {
        this.accountId = accountId;
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

    public void sendPacket(Object packet) {
        MessageContent message = new MessageContent(packet);
        channel.writeAndFlush(message);
    }

    public Channel getChannel() {
        return channel;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public Object removeAttribute(String key) {
        return attributes.remove(key);
    }

    public Object setAttributeIfAbsent(String key, Object value) {
        return attributes.putIfAbsent(key, value);
    }
}
