package com.server.publicsystem.guild.packet.bean;

import com.server.publicsystem.guild.model.GuildInfo;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/7/5 21:57
 */
public class GuildVO implements Serializable {

    /**
     * 工会id
     */
    private Long guildId;

    /**
     * 工会名称
     */
    private String name;

    /**
     * 成员数量
     */
    private Integer memberNum;

    private String leader;

    public static GuildVO valueOf(GuildInfo guildInfo) {
        GuildVO vo = new GuildVO();
        vo.guildId = guildInfo.getGuildId();
        vo.leader = guildInfo.getLeader();
        vo.memberNum = guildInfo.getMembers().size();
        vo.name = guildInfo.getName();
        return vo;
    }

    public Long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
}
