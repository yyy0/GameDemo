package com.server.user.fight.command;

import com.SpringContext;
import com.server.common.command.AbstractSceneCommand;
import com.server.log.LoggerUtil;
import com.server.user.account.model.Account;
import com.server.user.fight.FightAccount;

/**
 * @author yuxianming
 * @date 2019/6/15 20:34
 */
public class FightUseSkillCommand extends AbstractSceneCommand {

    /**
     * 释放技能本尊
     */
    private Account account;

    private int skillId;

    /**
     * 目标账号id
     */
    private String targetAccount;

    public FightUseSkillCommand(String accountId, int mapId) {
        super(accountId, mapId);
    }

    @Override
    public void action() {
        String accountId = getAccountId();
        int mapId = getMapId();

        FightAccount fightAccount = getMapInfo().getFightAccount(accountId);

        if (fightAccount == null) {
            LoggerUtil.error("玩家[{}]使用技能攻击玩家，找不到fightAccount,地图:{}", accountId, mapId);
        } else {
            SpringContext.getFightService().doUseSkill(fightAccount, targetAccount, skillId, getMapId());
        }
    }

    public static FightUseSkillCommand valueOf(Account account, String targetAccountId, int skillId) {
        FightUseSkillCommand cmd = new FightUseSkillCommand(account.getAccountId(), account.getMapId());
        cmd.account = account;
        cmd.skillId = skillId;
        cmd.targetAccount = targetAccountId;
        return cmd;
    }
}
