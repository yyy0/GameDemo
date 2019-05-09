package com.yxm.server;

import com.SpringContext;
import com.yxm.command.packet.CM_GMcommand;
import com.yxm.server.message.MessageContent;
import com.yxm.tool.ObjectByteUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;


/**
 * @author yuxianming
 * @date 2019/4/24 11:19
 */

public class GameServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(GameServerHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        MessageContent message = (MessageContent) msg;
        CM_GMcommand cm=(CM_GMcommand) message.getContent();
        String req=cm.getCommand();
        System.out.println("server 接收到客户端的请求： " + cm.getCommand());
        SpringContext.getCommandFacade().doCommand(ctx, req);
        String respStr = new StringBuilder("来自服务器的响应").append(req).toString();
        CM_GMcommand resp=CM_GMcommand.vauleOf(respStr);
        MessageContent messageContent1 = new MessageContent(ObjectByteUtil.objectToByteArray(resp).length, resp);
        ctx.writeAndFlush(messageContent1);

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

    private void handlerObject(ChannelHandlerContext ctx, Object msg) {

        Class c = msg.getClass();
        Field[] fields = c.getFields();
        for (Field f : fields) {
            System.out.println(c.getName() + "-成员：" + f.getName());
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器读取数据完毕");
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
}
