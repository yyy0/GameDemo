package com.yxm.server.message;

/**
 * @author yuxianming
 * @date 2019/5/6 23:10
 * 消息常量
 */
public class MessageConstants {
    // 协议开始标记
    public static final int HEAD_ID = 4;
    // 消息内容长度
    private static final int CONTENTLENGTH = 4;

    //
    public static final int BASE_LENGTH = HEAD_ID + CONTENTLENGTH;
}
