package com.server.publicsystem.guild.model;

import com.server.publicsystem.guild.constant.GuildPositionType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author yuxianming
 * @date 2019/7/4 21:15
 */
public class GuildInfo {

    private long guildId;

    private String name;

    private String leader;

    /**
     * 副会长
     */
    private String deputy;

    /**
     * 成员
     */
    private Map<String, GuildPositionType> members;

    /**
     * 申请列表
     */
    private Set<String> applyMembers = new HashSet<>();

    public static GuildInfo valueOf(long guildId, String name, String leader) {
        GuildInfo info = new GuildInfo();
        info.guildId = guildId;
        info.name = name;
        info.leader = leader;
        info.members = new HashMap<>();
        info.addMember(leader, GuildPositionType.LEADER);
        return info;

    }

    public void addMember(String accountId, GuildPositionType type) {
        members.put(accountId, type);
    }

    public void removeMember(String accountId) {
        members.remove(accountId);
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getDeputy() {
        return deputy;
    }

    public void setDeputy(String deputy) {
        this.deputy = deputy;
    }

    public Map<String, GuildPositionType> getCopyMembers() {
        if (members == null || members.size() == 0) {
            return null;
        }
        Map<String, GuildPositionType> map = new HashMap<>(members.size());
        for (Map.Entry<String, GuildPositionType> entry : members.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public Map<String, GuildPositionType> getMembers() {
        return members;
    }

    public void setMembers(Map<String, GuildPositionType> members) {
        this.members = members;
    }

    public Set<String> getApplyMembers() {
        return applyMembers;
    }

    public void setApplyMembers(Set<String> applyMembers) {
        this.applyMembers = applyMembers;
    }

    public void removeMembers(String accountId) {
        this.members.remove(accountId);
    }
}
