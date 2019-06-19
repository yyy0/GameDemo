package com.server.user.buff.model;

import com.server.user.buff.resource.BuffResource;
import com.server.user.fight.FightAccount;

/**
 * @author yuxianming
 * @date 2019/6/18 14:06
 */
public abstract class AbstractBuff {


    /**
     * buffId
     */
    protected int buffId;
    /**
     * 持续时间
     */
    protected long duration;
    /**
     * 开始时间
     */
    protected long createTime;
    /**
     * 周期时间
     */
    protected long period;

    /**
     * 上次生效时间（用于判断周期间隔时间）
     */
    protected long lastEffectTime;

    protected BuffResource buffResource;

    public void init(BuffResource resource) {
        this.buffResource = resource;
        this.buffId = resource.getId();
        this.duration = resource.getDuration();
        this.period = resource.getPeriodTime();
    }


    public void doAction(FightAccount fightAccount) {

    }

    public int getBuffId() {
        return buffId;
    }

    public void setBuffId(int buffId) {
        this.buffId = buffId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public BuffResource getBuffResource() {
        return buffResource;
    }

    public void setBuffResource(BuffResource buffResource) {
        this.buffResource = buffResource;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public long getLastEffectTime() {
        return lastEffectTime;
    }

    public void setLastEffectTime(long lastEffectTime) {
        this.lastEffectTime = lastEffectTime;
    }

    public boolean isExpire(long now) {
        return (now - createTime) > duration;
    }

    public boolean isCanAction(long now) {
        if (!isExpire(now) && period > 0) {
            return (now - lastEffectTime) > period;
        }
        return false;
    }
}
