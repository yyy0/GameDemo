package com.server.user.fight.syncStrategy;

import com.server.user.account.model.Account;
import com.server.user.attribute.AttributeUtil;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.Attribute;
import com.server.user.fight.FightAccount;

import java.util.HashMap;
import java.util.Map;

/**
 * 属性同步
 *
 * @author yuxianming
 * @date 2019/6/14 15:22
 */
public class AttributeSyncStrategy extends AbstractAccountSyncStrategy {

    private Map<AttributeType, Attribute> attributeMap = new HashMap<>();

    @Override
    public void syncInfo(FightAccount fightAccount) {
        fightAccount.setAttributeMap(attributeMap);
    }

    @Override
    public void init(Account account) {
        super.init(account);
        attributeMap = AttributeUtil.copy(account.getCurCopyAttribute());
    }

    public static AttributeSyncStrategy valueOf() {
        AttributeSyncStrategy syncStrategy = new AttributeSyncStrategy();
        return syncStrategy;
    }
}
