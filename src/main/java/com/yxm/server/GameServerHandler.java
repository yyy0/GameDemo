package com.yxm.server;

import com.yxm.command.CommandFacade;
import com.yxm.server.message.MessageContent;
import com.yxm.tool.ObjectByteUtil;
import com.yxm.user.account.Person;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Field;


/**
 * @author yuxianming
 * @date 2019/4/24 11:19
 */

public class GameServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(GameServerHandler.class);

    @Autowired
    private CommandFacade cmf;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        MessageContent messageContent = (MessageContent) msg;
        System.out.println(messageContent);
        Person p = (Person) messageContent.getContent();
        System.out.println(p);
        Person p2 = new Person("返回客户端消息", 100);

        MessageContent messageContent1 = new MessageContent(ObjectByteUtil.objectToByteArray(p2).length, p2);
        ctx.writeAndFlush(messageContent1);
//        ByteBuf bb = (ByteBuf) msg;
//
//        // 创建一个和buf同等长度的字节数组
//        byte[] reqByte = new byte[bb.readableBytes()];
//        // 将buf中的数据读取到数组中
//        bb.readBytes(reqByte);
//        String reqStr = new String(reqByte, Charset.forName("UTF-8"));
//        System.out.println("server 接收到客户端的请求： " + reqStr);
//        SpringContext.getCommandFacade().doCommand(ctx, reqStr);
//        String respStr = new StringBuilder("来自服务器的响应").append(reqStr).append("$_").toString();
//        //ChannelHandlerContext提供各种不同的操作用于触发不同的I/O时间和操作
//
//        ctx.writeAndFlush(Unpooled.copiedBuffer(respStr.getBytes()));
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
