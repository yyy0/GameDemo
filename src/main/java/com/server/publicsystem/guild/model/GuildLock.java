package com.server.publicsystem.guild.model;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 行会锁
 *
 * @author yuxianming
 * @date 2019/7/4 20:56
 */
public class GuildLock extends ReentrantLock {

    /**
     * 行会唯一id
     */
    private long guildId;

    public GuildLock(long guildId) {
        this.guildId = guildId;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }
}
