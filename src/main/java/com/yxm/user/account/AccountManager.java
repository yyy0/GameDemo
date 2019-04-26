package com.yxm.user.account;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuxianming
 * @date 2019/4/25 22:05
 */
public class AccountManager {

    /**
     * 所有账号集合
     */
    private ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }
}
