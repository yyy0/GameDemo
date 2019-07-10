package com.server.user.attribute.service;

import com.SpringContext;
import com.server.rank.event.FightPowerRefreshEvent;
import com.server.tool.TimeUtil;
import com.server.user.account.model.Account;
import com.server.user.attribute.constant.AttributeModel;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.AccountAttribute;
import com.server.user.attribute.model.Attribute;
import com.server.user.fight.syncStrategy.AttributeSyncStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/3 22:05
 */
@Component
public class AttributeManager {


    /**
     * 所有账号的属性集合
     */
    private Map<String, AccountAttribute> accountAttrs = new HashMap<>();

    public void loadAccountAttr(Account account) {
        String accountId = account.getAccountId();
        AccountAttribute accountAttribute = new AccountAttribute(account);
        accountAttrs.put(accountId, accountAttribute);
    }

    public void putAttributes(String accountId, AttributeModel model, Map<AttributeType, Attribute> attributes) {
        if (attributes == null) {
            throw new NullPointerException("设置属性不能为空！！");
        }
        AccountAttribute accountAttribute = accountAttrs.get(accountId);
        if (accountAttribute == null) {
            accountAttribute = new AccountAttribute();
        }
        accountAttribute.putAttributeModel(model, attributes);
        accountAttrs.put(accountId, accountAttribute);
    }

    public AccountAttribute getAccountAttribute(String accountId) {
        if (!accountAttrs.containsKey(accountId)) {
            return null;
        }
        return accountAttrs.get(accountId);
    }

    public long getFightPower(String accountId) {

        AccountAttribute accountAttribute = getAccountAttribute(accountId);
        if (accountAttribute == null) {
            return 0L;
        } else {
            return accountAttribute.getFightPower();
        }
    }


    public void refreshAttr(Account account, AttributeModel model) {
        AccountAttribute accountAttribute = getAccountAttribute(account.getAccountId());
        accountAttribute.putAttributeModel(model, model.getAttributeModel(account));
        accountAttribute.recompute();
        long oldFightPower = accountAttribute.getFightPower();
        long newFightPower = accountAttribute.calSelfFightPower();
        if (oldFightPower != newFightPower) {
            account.setFightPower(newFightPower);
            SpringContext.getEventManager().asyncSubmit(FightPowerRefreshEvent.valueOf(account, newFightPower, TimeUtil.now()));
        }
        account.fightSync(AttributeSyncStrategy.valueOf());
        SpringContext.getAccountService().saveAccountInfo(account);
    }


}
