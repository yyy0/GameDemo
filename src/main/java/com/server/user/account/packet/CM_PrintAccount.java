package com.server.user.account.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/10 15:27
 */
public class CM_PrintAccount implements Serializable {
    //账号id
    private String accountId;

    public static CM_PrintAccount valueOf(String accountId) {
        CM_PrintAccount req = new CM_PrintAccount();
        req.accountId = accountId;
        return req;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
