package com.yxm.server.message;

import com.yxm.tool.ObjectByteUtil;
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
            return;
        }
        // 防止数据过大 更新当前ByteBuf的readerIndex，更新后将跳过length字节的数据读取
        if (byteBuf.readableBytes() > 2048) {
            byteBuf.skipBytes(byteBuf.readableBytes());
        }

        //记录包头开始的index
        int beginReader;
        while (true) {
            beginReader = byteBuf.readerIndex();
            // 标记包头开始的index
            byteBuf.markReaderIndex();
            if (byteBuf.readInt() == MessageConstants.HEAD_ID) {
                break;
            }
            // 未读到包头，略过一个字节
            // 每次略过，一个字节，去读取，包头信息的开始标记
            byteBuf.resetReaderIndex();
            byteBuf.readByte();

            // 当略过，一个字节之后，
            // 数据包的长度，又变得不满足
            // 此时，应该结束。等待后面的数据到达
            if (byteBuf.readableBytes() < MessageConstants.BASE_LENGTH) {
                return;
            }
        }
        // 消息的长度
        int length = byteBuf.readInt();
        // 判断请求数据包数据是否到齐
        if (byteBuf.readableBytes() < length) {
            // 还原读指针
            byteBuf.readerIndex(beginReader);
            return;
        }


        // 读取消息内容
        byte[] content = new byte[length];
        byteBuf.readBytes(content);
        // byte[] content = byteBuf.readBytes(byteBuf.readableBytes()).array();
        Object obj = ObjectByteUtil.byteArrayToObject(content);
        MessageContent message = new MessageContent(length, obj);
        list.add(message);
    }
}
