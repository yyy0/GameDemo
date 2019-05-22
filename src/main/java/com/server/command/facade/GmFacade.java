package com.server.command.facade;

import com.SpringContext;
import com.server.command.packet.CM_GMcommand;
import com.server.dispatcher.HandlerAnno;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * @author yuxianming
 * @date 2019/5/14 21:29
 */
@Component
public class GmFacade {

    @HandlerAnno
    public void doGmCommand(TSession session, CM_GMcommand req) throws InvocationTargetException, IllegalAccessException {
        String str = req.getCommand();
        String accountId = SessionUtil.getAccountIdBySession(session);
        System.out.println("输出账户名称:" + accountId);
        SpringContext.getCommandFacade().doNewCommand(session, str);

    }
}
