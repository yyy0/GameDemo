package com.server.user.account.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/17 15:49
 */
public class SM_RegSuccess implements Serializable {

    private String accountId;

    private String name;

    public static SM_RegSuccess valueOf(String accountId, String name) {
        SM_RegSuccess packet = new SM_RegSuccess();
        packet.accountId = accountId;
        packet.name = name;
        return packet;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
