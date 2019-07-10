package com.server.publicsystem.guild.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/7/5 14:00
 */
public class CM_HandlerGuildApply implements Serializable {

    private String accountId;

    /**
     * 处理类型
     */
    private int type;

    public static CM_HandlerGuildApply valueOf(String accountId, int type) {
        CM_HandlerGuildApply packet = new CM_HandlerGuildApply();
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
