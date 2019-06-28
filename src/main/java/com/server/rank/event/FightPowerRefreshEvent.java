package com.server.rank.event;

import com.server.common.event.constant.EventId;
import com.server.common.event.event.IEvent;
import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/6/24 15:25
 */
public class FightPowerRefreshEvent implements IEvent {

    private Account account;

    private long fightPower;

    private long refreshTime;

    public static FightPowerRefreshEvent valueOf(Account account, long fightPower, long refreshTime) {
        FightPowerRefreshEvent event = new FightPowerRefreshEvent();
        event.account = account;
        event.fightPower = fightPower;
        event.refreshTime = refreshTime;
        return event;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getFightPower() {
        return fightPower;
    }

    public void setFightPower(long fightPower) {
        this.fightPower = fightPower;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }

    @Override
    public long getOwner() {
        return EventId.ONE.getId();
    }
}
