package com.server.user.equipUpgrade.facade;

import com.SpringContext;
import com.server.dispatcher.HandlerAnno;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import com.server.user.equipUpgrade.packet.CM_EquipUpGrade;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/6 11:38
 */
@Component
public class EquipUpGradeFacade {


    @HandlerAnno
    public void equipUpgrade(TSession session, CM_EquipUpGrade req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getEquipUpgradeService().equipUpgrade(account, req.getIndex());
    }
}
