package com.yxm.server;

import com.SpringContext;
import com.yxm.command.CommandFacade;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 * @author yuxianming
 * @date 2019/4/24 11:19
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private CommandFacade cmf;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        ByteBuf bb = (ByteBuf) msg;
        // 创建一个和buf同等长度的字节数组
        byte[] reqByte = new byte[bb.readableBytes()];
        // 将buf中的数据读取到数组中
        bb.readBytes(reqByte);
        String reqStr = new String(reqByte, Charset.forName("UTF-8"));
        System.out.println("server 接收到客户端的请求： " + reqStr);
        SpringContext.getCommandFacade().doCommand(ctx, reqStr);
        String respStr = new StringBuilder("来自服务器的响应").append(reqStr).append("$_").toString();
        //ChannelHandlerContext提供各种不同的操作用于触发不同的I/O时间和操作

        ctx.writeAndFlush(Unpooled.copiedBuffer(respStr.getBytes()));
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
        cause.printStackTrace();
        ctx.close();

    }
}
