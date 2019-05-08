package com.yxm.login;

import com.SpringContext;
import com.yxm.map.MapManager;
import com.yxm.map.MapResource;
import com.yxm.map.WorldService;
import com.yxm.user.account.Account;
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
    Logger logger = LoggerFactory.getLogger("登陆日志：");


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
    public void login(String accountId, String pwd) {
        Account account = SpringContext.getAccountService().getAccount(accountId);
        if (account == null) {
            logger.error("账号不存在");
            return;
        }
        if (!pwd.equals(account.getPwd())) {
            logger.error("密码错误！");
            return;
        }

        //登陆切图，没找打地图id 默认进新手村
        MapResource mapResource = mapManager.getResource(account.getMapId());
        if (mapResource == null) {
            worldService.enterMap(account, 1001);
        } else {
            worldService.enterMap(account, account.getMapId());
        }

    }

    public void reg(String accounId, String name, String pwd) {
        Account tempAcc = SpringContext.getAccountService().getAccount(accounId);
        if (tempAcc != null) {
            logger.error("该账号已存在！！账号id：[{}] 账号名称：[{}]", accounId, tempAcc.getName());
            return;
        }
        Account account = Account.valueOf(accounId, name, pwd);
        SpringContext.getAccountService().createAccount(account);

    }
}
