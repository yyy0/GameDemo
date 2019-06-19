package com.server.user.buff.model;

import com.SpringContext;
import com.server.tool.TimeUtil;
import com.server.user.buff.resource.BuffResource;

/**
 * buff信息
 *
 * @author yuxianming
 * @date 2019/6/18 11:19
 */
public class BuffInfo {

    private int buufId;

    private long createTime;

    private BuffResource resource;

    public static BuffInfo valueOf(int buffId) {
        BuffInfo buffInfo = new BuffInfo();
        buffInfo.buufId = buffId;
        buffInfo.resource = SpringContext.getBuffService().getBuffResource(buffId);
        return buffInfo;
    }

    /**
     * 持续时间
     *
     * @return
     */
    public int getDuration() {
        return resource.getDuration();
    }

    /**
     * 是否过期
     *
     * @return
     */
    public boolean isExpired() {
        return (TimeUtil.now() - createTime) >= getDuration();
    }

    public int getBuufId() {
        return buufId;
    }

    public void setBuufId(int buufId) {
        this.buufId = buufId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
