package com.server.publicsystem.guild.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/7/4 11:05
 */
public class CM_GuildInfo implements Serializable {

    private long guildId;

    public static CM_GuildInfo valueOf(long guildId) {
        CM_GuildInfo packet = new CM_GuildInfo();
        packet.guildId = guildId;
        return packet;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }
}
