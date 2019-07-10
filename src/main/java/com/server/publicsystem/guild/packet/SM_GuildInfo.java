package com.server.publicsystem.guild.packet;

import com.server.publicsystem.guild.constant.GuildPositionType;
import com.server.publicsystem.guild.model.GuildInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/7/5 11:47
 */
public class SM_GuildInfo implements Serializable {

    private long guildId;

    /**
     * 行会名称
     */
    private String guildName;

    /**
     * 工会成员
     */
    private Map<String, GuildPositionType> members;

    public static SM_GuildInfo valueOf(GuildInfo guildInfo) {
        SM_GuildInfo packet = new SM_GuildInfo();
        packet.guildId = guildInfo.getGuildId();
        packet.guildName = guildInfo.getName();
        packet.members = guildInfo.getCopyMembers();
        return packet;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public Map<String, GuildPositionType> getMembers() {
        return members;
    }

    public void setMembers(Map<String, GuildPositionType> members) {
        this.members = members;
    }
}
