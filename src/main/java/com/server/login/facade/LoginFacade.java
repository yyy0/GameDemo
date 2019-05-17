package com.server.login.facade;

import com.SpringContext;
import com.server.dispatcher.HandlerAnno;
import com.server.login.packet.CM_Login;
import com.server.login.packet.CM_Reg;
import com.server.session.model.TSession;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/5/10 15:33
 */
@Component
public class LoginFacade {

    @HandlerAnno
    public void login(TSession session, CM_Login req) {
        String accountId = req.getAccountId();
        String pwd = req.getPwd();
        SpringContext.getLoginService().login(session, accountId, pwd);
    }

    @HandlerAnno
    public void reg(TSession session, CM_Reg req) {
        String accountId = req.getAccountId();
        String name = req.getName();
        String pwd = req.getPwd();
        SpringContext.getLoginService().reg(session, accountId, name, pwd);
    }
}
