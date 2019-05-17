package com.server.server.message;

import com.server.tool.ObjectByteUtil;

/**
 * @author yuxianming
 * @date 2019/5/16 10:29
 */
public class MyMessage {

    //消息类型
    private short type;


    //主题信息的长度
    private int length;

    //主题信息
    private Object body;

    public MyMessage() {

    }

    public MyMessage(short type, int length, Object body) {
        this.type = type;
        this.length = length;
        this.body = body;
    }

    public MyMessage(short type, Object body) {
        this.type = type;
        this.length = ObjectByteUtil.objectToByteArray(body).length;
        this.body = body;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

}
