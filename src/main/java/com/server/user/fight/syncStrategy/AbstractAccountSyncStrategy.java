package com.server.user.fight.syncStrategy;

import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/6/14 15:20
 */
public abstract class AbstractAccountSyncStrategy implements ISyncStrategy {


    private String accountId;

    public void init(Account account) {
        this.accountId = account.getAccountId();
    }
}
