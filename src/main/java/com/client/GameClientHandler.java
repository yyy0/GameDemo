package com.client;

import com.yxm.server.message.MessageContent;
import com.yxm.tool.ObjectByteUtil;
import com.yxm.user.account.Person;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author yuxianming
 * @date 2019/4/24 12:30
 */
public class GameClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * Creates a client-side handler.
     */
//    public GameClientHandler() {
//        firstMessage = Unpooled.buffer(GameClient.SIZE);
//        for (int i = 0; i < firstMessage.capacity(); i++) {
//            firstMessage.writeByte((byte) i);
//        }
//    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        Person p = new Person("小明", 10);
        byte[] content = ObjectByteUtil.objectToByteArray(p);
        int length = content.length;
        MessageContent messageContent = new MessageContent(length, p);
        ctx.writeAndFlush(messageContent);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {

            MessageContent messageContent = (MessageContent) msg;
            Person p = (Person) messageContent.getContent();
            System.out.println(p);
//            ByteBuf bb = (ByteBuf) msg;
//            byte[] respByte = new byte[bb.readableBytes()];
//            bb.readBytes(respByte);
//            String respStr = new String(respByte, Charset.forName("UTF-8"));
//            System.out.println("客户端--收到响应：" + respStr);

            // 直接转成对象
//          handlerObject(ctx, msg);

        } finally {
            // 必须释放msg数据
            ReferenceCountUtil.release(msg);

        }
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
