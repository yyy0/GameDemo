package com.server.user.account.service;


import com.SpringContext;
import com.server.common.entity.CommonEntManager;
import com.server.map.model.MapInfo;
import com.server.tool.PacketSendUtil;
import com.server.user.account.entity.AccountEnt;
import com.server.user.account.model.Account;
import com.server.user.account.packet.SM_AccountInfo;
import com.server.user.account.packet.SM_FightAccountInfo;
import com.server.user.account.resource.AccountResource;
import com.server.user.fight.FightAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/4/28 22:50
 */
@Component
public class AccountService {

    /**
     * 通用manager
     */
    @Autowired
    private CommonEntManager<String, AccountEnt> commonEntManager;

    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    /**
     * 打印战斗账号信息
     *
     * @param account
     */
    public void printFightAccount(Account account) {
        MapInfo mapInfo = SpringContext.getMapManager().getMapInfo(account.getMapId());
        FightAccount fightAccount = mapInfo.getFightAccount(account.getAccountId());
        SM_FightAccountInfo packet = SM_FightAccountInfo.valueOf(fightAccount);
        PacketSendUtil.send(account, packet);
    }

    /**
     * 打印账号信息
     * @param account
     */
    public void printAccount(Account account) {
        System.out.println(account);
        SM_AccountInfo packet = SM_AccountInfo.valueOf(account.getAccountId(), account.getName(), account.getMapId()
                , account.getGridX(), account.getGirdY());
        PacketSendUtil.send(account, packet);
    }

    public Account getAccount(String accountId) {

        if (accountId == null) {
            return null;
        }
        AccountEnt ent = commonEntManager.getEnt(AccountEnt.class, accountId);
        if (ent != null) {
            ent.doDeserialize();
            return ent.getAccount();
        }
        return null;
    }

    public AccountEnt getAccountEnt(String accountId) {
        return commonEntManager.getEnt(AccountEnt.class, accountId);
    }

    public void saveAccountInfo(String accountId) {
        AccountEnt accountEnt = getAccountEnt(accountId);
        accountEnt.doSerialize();
        commonEntManager.update();
    }

    public void saveAccountInfo(Account account) {
        AccountEnt accountEnt = getAccountEnt(account.getAccountId());
        accountEnt.setAccount(account);
        accountEnt.doSerialize();
        commonEntManager.update();
    }

    public void createAccount(Account account) {
        AccountEnt accountEnt = AccountEnt.valueOf(account.getAccountId());
        accountEnt.setName(account.getName());
        accountEnt.setPwd(account.getPwd());
        accountEnt.setCreateTime(account.getCreateTime());
        accountEnt.setAccount(account);
        accountEnt.doSerialize();
        commonEntManager.createEnt(accountEnt);
        logger.info("创建新账号：账号id：[{}] 账号名称：[{}]", account.getAccountId(), account.getName());
        // accountManager.createEnt(account);
        //accountManager.createAccount(account);
    }

    public AccountResource getAccountResource(int id) {

        Map<Integer, Object> accountResource = SpringContext.getResourceManager().getResources(AccountResource.class.getSimpleName());
        AccountResource resource = (AccountResource) accountResource.get(id);
        if (resource == null) {
            logger.error("AccountResource找不到对应配置id：{0}" + id);
        }
        return resource;
    }


}
