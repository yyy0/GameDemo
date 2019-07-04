package com.server.publicsystem.guild.facade;

import com.SpringContext;
import com.server.dispatcher.HandlerAnno;
import com.server.publicsystem.guild.packet.*;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/7/4 11:24
 */
@Component
public class GuildFacade {


    @HandlerAnno
    public void createGuild(TSession session, CM_CreateGuild req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getGuildService().createGuild(account, req.getGuildName());
    }

    @HandlerAnno
    public void applyGuild(TSession session, CM_ApplyGuild req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getGuildService().applyGuild(account, req.getGuildId());
    }

    @HandlerAnno
    public void exitGuild(TSession session, CM_ExitGuild req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getGuildService().exitGuild(account);
    }

    @HandlerAnno
    public void printGuildList(TSession session, CM_GuildList req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getGuildService().printGuildList(account);
    }

    @HandlerAnno
    public void pringtGuildInfo(TSession session, CM_GuildInfo req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getGuildService().printGuildInfo(account, req.getGuildId());
    }

    @HandlerAnno
    public void changeGuildPosition(TSession session, CM_ChangeGuildPosition req) {
        Account account = SessionUtil.getAccountBySession(session);
    }
}
