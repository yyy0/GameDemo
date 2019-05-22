package com.client;

import com.SpringContext;
import com.client.clientframe.frame.LoginFrame;
import com.server.server.message.MessageContent;
import com.server.session.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author yuxianming
 * @date 2019/4/24 12:30
 */
public class GameClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        if (!SessionUtil.addChannelSession(ctx.channel())) {
            ctx.channel().close();
            return;
        }

        LoginFrame loginFrame = SpringContext.getLoginFrame();
        loginFrame.initFrame(ctx.channel());
//        new Thread(new SendMsg(ctx)).start();


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        MessageContent messageContent = (MessageContent) msg;
        Object packet = messageContent.getContent();
        SpringContext.getActionDispatcher().doClientHandle(packet);

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("客户端读取数据完毕");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        System.out.println("client 读取数据出现异常");
        cause.printStackTrace();
        ctx.close();
    }
}
