package com.server.server.message;

import com.server.server.constants.MessageConstants;
import com.server.tool.ObjectByteUtil;

/**
 * @author yuxianming
 * @date 2019/5/6 16:30
 * 消息内容 | 消息头 | 消息长度 | 消息内容 |
 */
public class MessageContent {

    /**
     * 消息头
     */
    private static final int headId = MessageConstants.HEAD_ID;
    /** 消息长度 */
    private final int length;
    /** 消息内容 */
    private Object content;

    public MessageContent(int length, Object content) {
        this.length = length;
        this.content = content;
    }

    public MessageContent(Object content) {
        byte[] bytes = ObjectByteUtil.objectToByteArray(content);
        this.length = bytes.length;
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public int getHeadId() {
        return headId;
    }

    public int getLength() {
        return length;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageContent{" +
                "HeadId=" + headId +
                "ContentLength=" + length +
                ", content='" + content + '\'' +
                '}';
    }
}
