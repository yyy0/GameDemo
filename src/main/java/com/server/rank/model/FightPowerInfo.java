package com.server.rank.model;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/24 10:17
 */
public class FightPowerInfo implements Comparable<FightPowerInfo>, Serializable {

    private String accountId;

    private long fightPower;

    private long refreshTime;

    public FightPowerInfo() {
    }

    public FightPowerInfo(String accountId, long fightPower) {
        this.accountId = accountId;
        this.fightPower = fightPower;
    }

    public static FightPowerInfo valueOf(String accountId, long fightPower, long refreshTime) {
        FightPowerInfo info = new FightPowerInfo();
        info.refreshTime = refreshTime;
        info.accountId = accountId;
        info.fightPower = fightPower;
        return info;
    }

    @Override
    public int compareTo(FightPowerInfo info) {
        if (this.fightPower > info.getFightPower()) {
            return -1;
        } else if (this.fightPower == info.getFightPower()) {
            if (this.refreshTime < info.getRefreshTime()) {
                return -1;
            } else if (this.refreshTime == info.refreshTime) {
                return this.accountId.compareTo(info.getAccountId());
            }
        }
        return 1;
    }

    public FightPowerInfo copy() {
        FightPowerInfo info = new FightPowerInfo();
        info.accountId = this.accountId;
        info.refreshTime = this.refreshTime;
        info.fightPower = this.fightPower;
        return info;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
}
