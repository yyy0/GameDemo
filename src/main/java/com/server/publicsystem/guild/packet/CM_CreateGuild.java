package com.server.publicsystem.guild.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/7/4 10:57
 */
public class CM_CreateGuild implements Serializable {

    private String guildName;

    public static CM_CreateGuild valueOf(String guildName) {
        CM_CreateGuild packet = new CM_CreateGuild();
        packet.guildName = guildName;
        return packet;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }
}
