package com.server.user.account.facade;

import com.SpringContext;
import com.server.dispatcher.HandlerAnno;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import com.server.user.account.packet.CM_PrintAccount;
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
    public void reg(TSession session, CM_PrintAccount req) {
        String accountId = req.getAccountId();
        Account account = SpringContext.getAccountService().getAccount(accountId);
        SpringContext.getAccountService().printAccount(account);
    }
}
