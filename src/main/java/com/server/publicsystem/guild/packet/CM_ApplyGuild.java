package com.server.publicsystem.guild.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/7/4 10:59
 */
public class CM_ApplyGuild implements Serializable {

    private long guildId;

    public static CM_ApplyGuild valueOf(long guildId) {
        CM_ApplyGuild packet = new CM_ApplyGuild();
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
