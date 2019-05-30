package com.server.common.identity.entity;

import javax.persistence.*;

/**
 * @author yuxianming
 * @date 2019/5/30 11:30
 */
@Entity
@Table(name = "identify")
public class IdentifyEnt {

    private static final int STEP = 5000;

    /**
     * 功能名称
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Transient
    private long now;

    /**
     * 唯一id
     */
    @Column(columnDefinition = "bigint(20) default 0", nullable = false)
    private volatile long value;

    public static IdentifyEnt valueOf(String id, long value) {
        IdentifyEnt result = new IdentifyEnt();
        result.id = id;
        result.now = value;
        result.value = value;
        return result;
    }

    public long getNextIdentify() {
        if (now == 0) {
            now = value;
        }
        if (now == value) {
            value += STEP;
        }
        return ++now;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
