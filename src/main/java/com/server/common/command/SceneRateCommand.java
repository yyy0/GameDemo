package com.server.common.command;

import com.server.map.model.MapInfo;
import com.server.monster.model.Monster;
import com.server.user.fight.FightAccount;

import java.util.List;
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

        List<Monster> monsters = mapInfo.getMonsters();
        if (fightAccounts.size() > 0) {
            //处理buff
            for (FightAccount account : fightAccounts.values()) {
                if (account.isHasBuff()) {
                    account.handleBuff();
                }

            }
        }

        //处理怪物
        if (monsters.size() > 0) {
            for (Monster monster : monsters) {
                if (monster.isHasBuff()) {
                    monster.handleBuff();
                }

            }
        }
    }

    public static SceneRateCommand valueOf(String accountId, int mapId, long delay, long period) {

        SceneRateCommand command = new SceneRateCommand(accountId, mapId, delay, period);
        return command;
    }
}
