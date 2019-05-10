package com.yxm.login.facade;

import com.SpringContext;
import com.yxm.dispatcher.HandlerAnno;
import com.yxm.login.packet.CM_Login;
import com.yxm.login.packet.CM_Reg;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/5/10 15:33
 */
@Component
public class LoginFacade {

    @HandlerAnno
    public void login(CM_Login req) {
        String accountId = req.getAccountId();
        String pwd = req.getPwd();
        SpringContext.getLoginService().login(accountId, pwd);
    }

    @HandlerAnno
    public void reg(CM_Reg req) {
        String accountId = req.getAccountId();
        String name = req.getName();
        String pwd = req.getPwd();
        SpringContext.getLoginService().reg(accountId, name, pwd);
    }
}
