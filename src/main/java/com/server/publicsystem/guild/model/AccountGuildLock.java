package com.server.publicsystem.guild.model;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuxianming
 * @date 2019/7/5 10:25
 */
public class AccountGuildLock extends ReentrantLock {

    private String accountId;


    public AccountGuildLock(String accountId) {
        this.accountId = accountId;
    }


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
