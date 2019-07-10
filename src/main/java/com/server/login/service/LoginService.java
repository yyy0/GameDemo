package com.server.login.service;

import com.SpringContext;
import com.server.map.resource.MapResource;
import com.server.map.service.MapManager;
import com.server.map.service.WorldService;
import com.server.publicsystem.i18n.constant.I18Message;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.task.model.AccountTask;
import com.server.task.packet.SM_TaskInfo;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;
import com.server.user.account.packet.SM_LoginSuccess;
import com.server.user.account.packet.SM_RegFail;
import com.server.user.account.packet.SM_RegSuccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/4/26 12:24
 */
@Component
public class LoginService {
    private Logger logger = LoggerFactory.getLogger("登陆日志：");

    @Autowired
    private MapManager mapManager;

    @Autowired
    private WorldService worldService;

    /**
     * 登陆
     *
     * @param accountId
     * @param pwd
     */
    public void login(TSession session, String accountId, String pwd) {
        Account account = SpringContext.getAccountService().getAccount(accountId);
        if (account == null) {
            logger.error("账号不存在");
            return;
        }
        if (!pwd.equals(account.getPwd())) {
            logger.error("密码错误！");
            return;
        }

        if (session.getAccountId() == null) {
            session.setAttribute(SessionUtil.ACCOUNT_ID, accountId);
            SpringContext.getSessionService().register(session, accountId);
        }

        //登陆时加载属性
        SpringContext.getAttributeManager().loadAccountAttr(account);

        //登陆切图，没找到地图id 默认进新手村
        MapResource mapResource = SpringContext.getWorldService().getMapResource(account.getMapId());
        int mapId;
        if (mapResource == null) {
            worldService.enterMap(account, 1001);
            mapId = 1;
        } else {
            mapId = account.getMapId();
            worldService.enterMap(account, mapId);
        }

        SM_LoginSuccess packet = SM_LoginSuccess.valueOf(accountId, mapId);
        PacketSendUtil.send(session, packet);

        AccountTask accountTask = SpringContext.getTaskService().getAccountTask(accountId);
        SM_TaskInfo taskPacket = SM_TaskInfo.valueOf(accountTask);
        PacketSendUtil.send(session, taskPacket);

    }

    public void reg(TSession session, String accountId, String name, String pwd) {
        Account tempAcc = SpringContext.getAccountService().getAccount(accountId);
        if (tempAcc != null) {
            logger.error("该账号已存在！！账号id：[{}] 账号名称：[{}]", accountId, tempAcc.getName());

            SM_RegFail packet = SM_RegFail.valueOf(I18Message.REG_FAIL_ACCOUNT_EXIS);
            PacketSendUtil.send(session, packet);
            return;
        }
        Account account = Account.valueOf(accountId, name, pwd);
        SpringContext.getAccountService().createAccount(account);
        session.setAttribute(SessionUtil.ACCOUNT_ID, accountId);
        SpringContext.getSessionService().register(session, accountId);
        SM_RegSuccess packet = SM_RegSuccess.valueOf(accountId, name);
        PacketSendUtil.send(session, packet);

    }
}
