package com.server.server.message;

import com.server.tool.ObjectByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author yuxianming
 * @date 2019/5/16 10:51
 */
public class MyEncoder extends MessageToByteEncoder<MyMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyMessage myMessage, ByteBuf byteBuf) throws Exception {
        if (myMessage == null) {
            throw new Exception("未获得消息内容");
        }


        Object msgBody = myMessage.getBody();
        byte[] bytes = ObjectByteUtil.objectToByteArray(msgBody);
        byteBuf.writeShort(myMessage.getType());
        byteBuf.writeInt(myMessage.getLength());
        byteBuf.writeBytes(bytes);

    }
}
