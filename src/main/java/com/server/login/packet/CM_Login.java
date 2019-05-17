package com.server.login.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/10 15:26
 */
public class CM_Login implements Serializable {
    /**
     * 账号id、密码
     */
    private String accountId;
    private String pwd;

    public static CM_Login valueOf(String accountId, String pwd) {
        CM_Login req = new CM_Login();
        req.accountId = accountId;
        req.pwd = pwd;
        return req;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
