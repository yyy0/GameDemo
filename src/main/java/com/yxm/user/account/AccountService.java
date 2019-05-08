package com.yxm.user.account;


import com.yxm.common.CommonManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/4/28 22:50
 */
@Component
public class AccountService {


    /**
     * 通用manager
     */
    @Autowired
    private CommonManager<String, AccountEnt> commonManager;


    Logger logger = LoggerFactory.getLogger(AccountService.class);

    /**
     * 打印账号信息
     *
     * @param account
     */
    public void printAccount(Account account) {
        System.out.println(account);
    }

    public Account getAccount(String accountId) {

        AccountEnt ent = commonManager.getEnt(AccountEnt.class, accountId);
        ent.doDeserialize();
        //Account account=accountManager.getAccount(accountId);
        return ent == null ? null : ent.getAccount();
    }

    public AccountEnt getAccountEnt(String accountId) {
        return commonManager.getEnt(AccountEnt.class, accountId);
    }

    public void saveAccountInfo(String accountId) {
        AccountEnt accountEnt = getAccountEnt(accountId);
        accountEnt.doSerialize();
        commonManager.update(accountEnt);
    }

    public void saveAccountInfo(String accountId, Account account) {
        AccountEnt accountEnt = getAccountEnt(accountId);
        accountEnt.setAccount(account);
        accountEnt.doSerialize();
        commonManager.update(accountEnt);
    }

    public void createAccount(Account account) {
        AccountEnt accountEnt = AccountEnt.valueOf(account.getAccountId());
        accountEnt.setName(account.getName());
        accountEnt.setPwd(account.getPwd());
        accountEnt.setCreateTime(account.getCreateTime());
        accountEnt.setAccount(account);
        accountEnt.doSerialize();
        commonManager.createEnt(accountEnt);
        logger.info("创建新账号：账号id：[{}] 账号名称：[{}]", account.getAccountId(), account.getName());
        // accountManager.createEnt(account);
        //accountManager.createAccount(account);
    }


}
