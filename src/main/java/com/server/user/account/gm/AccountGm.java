package com.server.user.account.gm;

import com.SpringContext;
import com.server.gm.anno.GmAnno;
import com.server.gm.anno.GmMethod;
import com.server.user.account.model.Account;
import com.server.user.account.packet.CM_PrintAccount;
import com.server.user.account.packet.CM_PrintFightAccount;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/5/21 12:01
 */
@Component
@GmAnno(title = "账号GM")
public class AccountGm {

    @GmMethod(name = "打印账号信息", clz = CM_PrintAccount.class)
    public void printAccountInfo(Account account) {
        SpringContext.getAccountService().printAccount(account);
    }

    @GmMethod(name = "打印战斗账号信息", clz = CM_PrintFightAccount.class)
    public void printFightAccount(Account account) {
        SpringContext.getAccountService().printFightAccount(account);
    }
}
