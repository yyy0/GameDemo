package com.server.user.fight.facade;

import com.SpringContext;
import com.server.dispatcher.HandlerAnno;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import com.server.user.fight.packet.CM_UseSkill;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/14 18:05
 */
@Component
public class PvpFacade {

    @HandlerAnno
    public void useSkill(TSession session, CM_UseSkill req) {
        Account account = SessionUtil.getAccountBySession(session);

        SpringContext.getFightService().useSkill(account, req.getTargetAccountId(), req.getSkillId());
    }
}
