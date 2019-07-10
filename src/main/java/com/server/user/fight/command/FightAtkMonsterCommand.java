package com.server.user.fight.command;

import com.SpringContext;
import com.server.common.command.AbstractSceneCommand;
import com.server.log.LoggerUtil;
import com.server.map.model.Scene;
import com.server.user.account.model.Account;
import com.server.user.fight.FightAccount;

/**
 * @author yuxianming
 * @date 2019/6/20 21:02
 */
public class FightAtkMonsterCommand extends AbstractSceneCommand {

    private int skillId;

    private long monsterGid;

    public FightAtkMonsterCommand(String accountId, Scene scene) {
        super(accountId, scene);
    }

    public FightAtkMonsterCommand(String accountId, int mapId) {
        super(accountId, mapId);
    }

    @Override
    public void action() {
        String accountId = getAccountId();
        int mapId = getMapId();

        FightAccount fightAccount = getScene().getFightAccount(accountId);

        if (fightAccount == null) {
            LoggerUtil.error("玩家[{}]使用技能攻击怪物，找不到fightAccount,地图:{}", accountId, mapId);
        } else {
            SpringContext.getFightService().doAtkMonster(fightAccount, skillId, monsterGid, mapId);
        }
    }

    public static FightAtkMonsterCommand valueOf(Account account, int skillId, long monsterGid) {
        FightAtkMonsterCommand command = new FightAtkMonsterCommand(account.getAccountId(), account.getMapId());
        command.skillId = skillId;
        command.monsterGid = monsterGid;
        return command;
    }
}
