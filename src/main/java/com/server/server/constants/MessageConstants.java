package com.server.server.constants;

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
    // 消息内容最大长度
    public static final int MAX_LENGTH = 2048;

    //基本长度
    public static final int BASE_LENGTH = HEAD_ID + CONTENTLENGTH;


    public static final int MAX_FRAME_LENGTH = 1024 * 1024;
    public static final int LENGTH_FIELD_LENGTH = 4;
    public static final int LENGTH_FIELD_OFFSET = 2;
    public static final int HEAD_LENGTH = LENGTH_FIELD_LENGTH + LENGTH_FIELD_OFFSET;
    public static final int LENGTH_ADJUSTMENT = 0;
    public static final int INITIAL_BYTES_TO_STRIP = 0;


}
