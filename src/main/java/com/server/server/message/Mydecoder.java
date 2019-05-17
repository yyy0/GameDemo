package com.server.server.message;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;

/**
 * @author yuxianming
 * @date 2019/5/16 10:26
 */
public class Mydecoder extends LengthFieldBasedFrameDecoder {

    private static final Logger logger = LoggerFactory.getLogger(Mydecoder.class);

    public Mydecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    public Mydecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public Mydecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public Mydecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }


//    @Override
//    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
//        if(in == null){
//            return null;
//        }
//        in.markReaderIndex();
//        if(in.readableBytes() < MessageConstants.HEAD_LENGTH){
//            throw new Exception("错误的消息,长度太短");
//        }
//
//        /**
//         * 通过源码我们能看到在读的过程中
//         * 每读一次读过的字节即被抛弃
//         * 即指针会往前跳
//         */
//        short type = in.readShort();
//
//        int length = in.readInt();
//
//
//        if(in.readableBytes() < length){
//            in.resetReaderIndex();
//            logger.error("消息长度不够");
//            return null;
//        }
//
//        ByteBuf buf = in.readBytes(length);
//        byte[] msgBody = new byte[buf.readableBytes()];
//        buf.readBytes(msgBody);
//
//        Object obj = ObjectByteUtil.byteArrayToObject(msgBody);
//        MyMessage msg=new MyMessage(type,length,msgBody);
//        return msg;
//    }

}
