package com.server.server.message;

import com.server.server.constants.MessageConstants;
import com.server.tool.ObjectByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author yuxianming
 * @date 2019/5/6 15:56
 * 解码器
 */
public class MessageDecoder extends ByteToMessageDecoder {


    private static final Logger logger = LoggerFactory.getLogger(MessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        if (byteBuf.readableBytes() < MessageConstants.BASE_LENGTH) {
            logger.error("读的消息长度不够消息头");
            return;
        }
        // 防止数据过大 更新当前ByteBuf的readerIndex，更新后将跳过length字节的数据读取
        if (byteBuf.readableBytes() > MessageConstants.MAX_FRAME_LENGTH) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            logger.error("读的消息太长！！！");
            return;
        }

        //记录包头开始的index
        byteBuf.markReaderIndex();
        //消息头
        int headId = byteBuf.readInt();

        // 消息的长度
        int length = byteBuf.readInt();
        // 判断请求数据包数据是否到齐
        if (byteBuf.readableBytes() < length) {
            // 还原读指针
            byteBuf.resetReaderIndex();
            logger.error("读到的消息体长度小于发送方的长度！！");
            return;
        }

        // 读取消息内容
        byte[] content = new byte[length];
        byteBuf.readBytes(content);
        Object obj = ObjectByteUtil.byteArrayToObject(content);
        MessageContent message = new MessageContent(length, obj);
        list.add(message);
    }
}
