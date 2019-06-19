package com.server.user.fight.syncStrategy;

import com.server.user.account.model.Account;
import com.server.user.fight.FightAccount;
import com.server.user.skill.model.AccountSkill;

/**
 * @author yuxianming
 * @date 2019/6/14 16:12
 */
public class SkillSyncStrategy extends AbstractAccountSyncStrategy {

    private AccountSkill accountSkill;

    @Override
    public void init(Account account) {
        super.init(account);
        accountSkill = account.getAccountSkill().copy();
    }

    @Override
    public void syncInfo(FightAccount fightAccount) {
        fightAccount.setAccountSkill(accountSkill);
    }


}
