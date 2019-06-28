package com.server.common.event.constant;

/**
 * @author yuxianming
 * @date 2019/6/24 21:17
 */
public enum EventId {

    /**
     * 事件线程编号：“1” 暂定为排行榜相关事件使用
     */
    ONE(1),
    TWO(2);


    private int id;

    EventId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
