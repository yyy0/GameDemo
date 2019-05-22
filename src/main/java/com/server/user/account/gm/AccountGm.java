package com.server.user.account.gm;

import com.SpringContext;
import com.server.command.anno.GmAnno;
import com.server.command.anno.GmMethod;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/5/21 12:01
 */
@Component
@GmAnno(title = "账号GM")
public class AccountGm {

    @GmMethod(name = "打印账号信息")
    public void printAccountInfo(Account account) {
        SpringContext.getAccountService().printAccount(account);
    }
}
