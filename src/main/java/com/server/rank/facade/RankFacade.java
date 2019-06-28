package com.server.rank.facade;

import com.SpringContext;
import com.server.common.event.anno.ReceiverAnno;
import com.server.dispatcher.HandlerAnno;
import com.server.rank.event.FightPowerRefreshEvent;
import com.server.rank.model.FightPowerInfo;
import com.server.rank.packet.CM_RankInfo;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/22 17:13
 */
@Component
public class RankFacade {

    @HandlerAnno
    public void printRank(TSession session, CM_RankInfo req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getRankService().printRankInfo(account);
    }

    @ReceiverAnno
    public void updateFightPowerRank(FightPowerRefreshEvent event) {

        FightPowerInfo info = FightPowerInfo.valueOf(event.getAccount().getAccountId(), event.getFightPower(), event.getRefreshTime());
        SpringContext.getRankService().updateRank(info);
    }
}
