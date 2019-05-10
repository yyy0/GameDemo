package com.yxm.server.message;

/**
 * @author yuxianming
 * @date 2019/5/6 16:30
 * 消息内容 | Version | Content-Length | Content |
 */
public class MessageContent {

    private static final int headId = MessageConstants.HEAD_ID;
    private final int length;

    private Object content;

    public MessageContent(int length, Object content) {
        this.length = length;
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
