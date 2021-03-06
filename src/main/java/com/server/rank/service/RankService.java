package com.server.rank.service;

import com.SpringContext;
import com.server.common.entity.CommonEntManager;
import com.server.rank.model.FightPowerInfo;
import com.server.rank.model.FightPowerRank;
import com.server.rank.packet.SM_FightPowerRank;
import com.server.tool.PacketSendUtil;
import com.server.tool.TimeUtil;
import com.server.user.account.entity.AccountEnt;
import com.server.user.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yuxianming
 * @date 2019/6/24 11:49
 */
@Component
public class RankService {


    @Autowired
    private FightPowerRank fightPowerRank;

    @Autowired
    private CommonEntManager<String, AccountEnt> commonEntManager;

    public void initRank() {
        List<AccountEnt> accountEntList = commonEntManager.namedQuery("loadFightPowerRank", AccountEnt.class);
        long now = TimeUtil.now();
        for (AccountEnt ent : accountEntList) {
            ent.doDeserialize();
            Account account = ent.getAccount();
            SpringContext.getAttributeManager().loadAccountAttr(account);
            long fightPower = SpringContext.getAttributeManager().getFightPower(account.getAccountId());
            fightPowerRank.addInfo(FightPowerInfo.valueOf(ent.getAccountId(), fightPower, now));
        }
        fightPowerRank.sortRank();


    }

    /**
     * 打印排行榜
     *
     * @param account
     */
    public void printRankInfo(Account account) {
        CopyOnWriteArrayList<FightPowerInfo> list = fightPowerRank.getFightPowerList();
        SM_FightPowerRank packet = SM_FightPowerRank.valueOf(list);
        PacketSendUtil.send(account, packet);
    }


    /**
     * 更新排行榜
     */
    public void updateRank(FightPowerInfo info) {
        fightPowerRank.updateRank(info);
    }
}
