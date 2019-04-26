package com.yxm.user.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/4/25 17:35
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {
    @Id
    private String accountId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "pwd", nullable = false)
    private String pwd;

    public static Account valueOf(String account, String name) {
        Account info = new Account();
        info.accountId = account;
        info.name = name;
        return info;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
