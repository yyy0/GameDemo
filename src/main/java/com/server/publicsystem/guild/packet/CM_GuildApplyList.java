package com.server.publicsystem.guild.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/7/6 20:22
 */
public class CM_GuildApplyList implements Serializable {

    private long guildId;

    public static CM_GuildApplyList valueOf(long guildId) {
        CM_GuildApplyList packet = new CM_GuildApplyList();
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
