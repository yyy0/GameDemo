package com.server.publicsystem.guild.packet;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yuxianming
 * @date 2019/7/6 20:31
 */
public class SM_GuildApplyList implements Serializable {

    private Set<String> members;

    public static SM_GuildApplyList valueOf(Set<String> members) {
        SM_GuildApplyList packet = new SM_GuildApplyList();
        packet.members = new HashSet<>(members);
        return packet;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }
}
