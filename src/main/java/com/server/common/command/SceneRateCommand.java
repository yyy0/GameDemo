package com.server.common.command;

import com.server.map.model.MapInfo;
import com.server.user.fight.FightAccount;

import java.util.Map;

/**
 * 场景周期command 用于周期性的执行任务
 *
 * @author yuxianming
 * @date 2019/6/18 10:22
 */
public class SceneRateCommand extends AbstractSceneRateCommand {

    public SceneRateCommand(String accountId, int mapId, long delay, long period) {
        super(accountId, mapId, delay, period);
    }

    @Override
    public void action() {

        // LoggerUtil.info("定时执行场景处理任务,地图id：" + getMapId());
        MapInfo mapInfo = getMapInfo();
        Map<String, FightAccount> fightAccounts = mapInfo.getFightAccountMap();
        if (fightAccounts.size() == 0) {
            return;
        }
        //处理buff
        for (FightAccount account : fightAccounts.values()) {
            if (account.isHasBuff()) {
                account.handleBuff();
            }

        }
    }

    public static SceneRateCommand valueOf(String accountId, int mapId, long delay, long period) {

        SceneRateCommand command = new SceneRateCommand(accountId, mapId, delay, period);
        return command;
    }
}
