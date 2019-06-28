package com.server.server;

import com.SpringContext;
import com.server.server.message.MessageContent;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @author yuxianming
 * @date 2019/4/24 11:19
 */

public class GameServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(GameServerHandler.class);
    private int i = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (!SessionUtil.addChannelSession(ctx.channel())) {
            ctx.channel().close();
            return;
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {


        TSession session = SessionUtil.getSessionByChannel(ctx.channel());
        if (session == null) {
            return;
        }
        MessageContent message = (MessageContent) msg;
        Object packet = message.getContent();
        SpringContext.getActionDispatcher().handle(session, packet);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel channel = ctx.channel();
        if (channel.isOpen() || channel.isActive()) {
            channel.close();
        }
        if (!(cause instanceof IOException)) {
            logger.error("channel:" + channel.remoteAddress(), cause);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        TSession session = SessionUtil.getSessionByChannel(ctx.channel());

        if (session != null) {
            SpringContext.getSessionService().unregister(session);
        }
    }
}
