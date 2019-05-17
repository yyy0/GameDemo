package com.server.server.message;

import com.server.tool.ObjectByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yuxianming
 * @date 2019/5/6 15:59
 * 编码器
 */
public class MessageEncoder extends MessageToByteEncoder<MessageContent> {

    private static final Logger logger = LoggerFactory.getLogger(MessageEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageContent message, ByteBuf byteBuf) throws Exception {

        // 这里写入的顺序就是协议的顺序
        // 写入Header、length信息
        byteBuf.writeInt(message.getHeadId());
        byteBuf.writeInt(message.getLength());

        byte[] bytes = ObjectByteUtil.objectToByteArray(message.getContent());
        // 写入消息主体信息
        byteBuf.writeBytes(bytes);
    }
}
