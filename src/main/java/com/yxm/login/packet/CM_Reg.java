package com.yxm.login.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/10 15:25
 */
public class CM_Reg implements Serializable {
    //账号id
    private String accountId;
    //账号密码
    private String pwd;
    //账号名称
    private String name;

    public static CM_Reg valueOf(String accountId, String name, String pwd) {
        CM_Reg req = new CM_Reg();
        req.accountId = accountId;
        req.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
