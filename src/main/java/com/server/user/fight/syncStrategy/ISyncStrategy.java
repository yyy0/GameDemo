package com.server.user.fight.syncStrategy;

import com.server.user.fight.FightAccount;

/**
 * @author yuxianming
 * @date 2019/6/14 15:19
 */
public interface ISyncStrategy {

    /**
     * 从账号上同步数据
     *
     * @param fightAccount
     */
    void syncInfo(FightAccount fightAccount);


}
