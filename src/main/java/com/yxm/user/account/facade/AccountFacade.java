package com.yxm.user.account.facade;

import com.SpringContext;
import com.yxm.dispatcher.HandlerAnno;
import com.yxm.user.account.model.Account;
import com.yxm.user.account.packet.CM_PrintAccount;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/5/10 15:24
 */
@Component
public class AccountFacade {

    /**
     * 打印账号信息
     *
     * @param req
     */
    @HandlerAnno
    public void reg(CM_PrintAccount req) {
        String accountId = req.getAccountId();
        Account account = SpringContext.getAccountService().getAccount(accountId);
        SpringContext.getAccountService().printAccount(account);
    }
}
