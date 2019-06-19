package com.server.user.fight.command;

import com.server.common.command.AbstractSceneCommand;
import com.server.map.model.MapInfo;
import com.server.user.fight.FightAccount;
import com.server.user.fight.syncStrategy.ISyncStrategy;

/**
 * @author yuxianming
 * @date 2019/6/14 11:48
 */
public class FightSyncCommand extends AbstractSceneCommand {


    /**
     * 同步策略
     */
    private ISyncStrategy strategy;

    public FightSyncCommand(String accountId, int mapId) {
        super(accountId, mapId);
    }

    public static FightSyncCommand valueOf(ISyncStrategy syncStrategy, String accountId, int mapId) {
        FightSyncCommand command = new FightSyncCommand(accountId, mapId);
        command.strategy = syncStrategy;
        return command;
    }

    @Override
    public void action() {
        MapInfo mapInfo = getMapInfo();
        FightAccount fightAccount = mapInfo.getFightAccount(getAccountId());
        strategy.syncInfo(fightAccount);
    }

}
