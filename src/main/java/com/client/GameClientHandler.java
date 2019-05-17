package com.client;

import com.SpringContext;
import com.server.server.message.MessageContent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author yuxianming
 * @date 2019/4/24 12:30
 */
public class GameClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {


//        for(int i=0;i<100;i++) {
//            CM_Reg req = CM_Reg.valueOf("jerry", "杰瑞", "jerry123");
//            MessageContent message = new MessageContent(req);
////            MyMessage message=new MyMessage((short)111,req);
//            ctx.writeAndFlush(message);
//        }
//
//        CM_Login req2 = CM_Login.valueOf("jack", "jack123");
//        byte[] bytes2 = ObjectByteUtil.objectToByteArray(req2);
//        MessageContent message2 = new MessageContent(bytes2.length, req2);
//        ctx.writeAndFlush(message2);
//        TSession session= SpringContext.getSessionService().create(ctx.channel());
//        CM_Login loginReq=CM_Login.valueOf("yyy","pwd123");
//        MessageContent loginMessage=new MessageContent(loginReq);
//        ctx.writeAndFlush(loginMessage);
        new Thread(new SendMsg(ctx)).start();



    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

            MessageContent messageContent = (MessageContent) msg;
        Object packet = messageContent.getContent();
        SpringContext.getActionDispatcher().doClientHandle(packet);

//            ByteBuf bb = (ByteBuf) msg;
//            byte[] respByte = new byte[bb.readableBytes()];
//            bb.readBytes(respByte);
//            String respStr = new String(respByte, Charset.forName("UTF-8"));
//            System.out.println("客户端--收到响应：" + respStr);

    }

    private void handlerObject(ChannelHandlerContext ctx, Object msg) {


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
