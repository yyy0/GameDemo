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
        TSession session = SessionUtil.getSessionByChannel(ctx.channel());
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
        SpringContext.getActionDispatcher().doHandle(session, packet);
//        CM_GMcommand cm=(CM_GMcommand) message.getContent();
//        String req=cm.getCommand();
//        System.out.println("server 接收到客户端的请求： " + cm.getCommand());
//        SpringContext.getCommandFacade().doCommand(ctx, req);
//        String respStr = new StringBuilder("来自服务器的响应").append(req).toString();
//        CM_GMcommand resp=CM_GMcommand.vauleOf(respStr);
//        MessageContent messageContent1 = new MessageContent(ObjectByteUtil.objectToByteArray(resp).length, resp);
//        ctx.writeAndFlush(messageContent1);

//        ctx.writeAndFlush(Unpooled.copiedBuffer(respStr.getBytes()));
//        Person p = (Person) messageContent.getContent();
//        System.out.println(p);
//        Person p2 = new Person("返回客户端消息", 100);
//
//        ByteBuf bb = (ByteBuf) msg;
//
//        // 创建一个和buf同等长度的字节数组
//        byte[] reqByte = new byte[bb.readableBytes()];
//        // 将buf中的数据读取到数组中
//        bb.readBytes(reqByte);
//        String reqStr = new String(reqByte, Charset.forName("UTF-8"));
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
        String accountId = SessionUtil.getAccountIdBySession(session);
        if (session != null) {
            SpringContext.getSessionService().unregister(session);
        }
    }
}
