package com.server.publicsystem.guild.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/7/4 11:02
 */
public class CM_ChangeGuildPosition implements Serializable {

    private String accountId;

    private int type;

    public static CM_ChangeGuildPosition valueOf(String accountId, int type) {
        CM_ChangeGuildPosition packet = new CM_ChangeGuildPosition();
        packet.accountId = accountId;
        packet.type = type;
        return packet;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
