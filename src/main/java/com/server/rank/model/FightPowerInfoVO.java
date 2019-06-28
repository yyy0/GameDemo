package com.server.rank.model;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/24 18:21
 */
public class FightPowerInfoVO implements Serializable {

    private String accountId;

    private Long fightPower;

    public static FightPowerInfoVO valueOf(FightPowerInfo fightPowerInfo) {
        FightPowerInfoVO infoVO = new FightPowerInfoVO();
        infoVO.accountId = fightPowerInfo.getAccountId();
        infoVO.fightPower = fightPowerInfo.getFightPower();
        return infoVO;
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
}
