package com.server.user.fight.service;

import com.SpringContext;
import com.server.map.model.Grid;
import com.server.map.model.MapInfo;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.tool.PacketSendUtil;
import com.server.tool.TimeUtil;
import com.server.user.account.model.Account;
import com.server.user.attribute.constant.GlobalConstant;
import com.server.user.buff.model.AbstractBuff;
import com.server.user.fight.FightAccount;
import com.server.user.fight.command.FightUseSkillCommand;
import com.server.user.fight.packet.SM_Attacker;
import com.server.user.fight.packet.SM_Hit;
import com.server.user.skill.resource.SkillResource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yuxianming
 * @date 2019/6/14 18:15
 */
@Component
public class FightService {

    public void doUseSkill(FightAccount fightAccount, String targetAccountId, int skillId, int mapId) {

        //主角是否能释放技能（是否有该技能, 蓝量, cd等条件够不够）
        if (!fightAccount.isCanUseSkill(skillId)) {
            return;
        }
        SkillResource skillResource = SpringContext.getSkillService().getSkillResource(skillId);
        int targetNum = skillResource.getTargetNum();
        if (targetNum <= 0) {
            I18Utils.notifyMessageThrow(fightAccount.getAccountId(), I18nId.ILLEGAL_VALUE);
        }
        MapInfo mapInfo = SpringContext.getMapManager().getMapInfo(mapId);
        FightAccount targetAccount = mapInfo.getFightAccount(targetAccountId);

        //是否在攻击范围内
        if (!isCanAttackTarget(fightAccount, mapId, targetAccount, skillResource.getRange())) {
            return;
        }

        //真正使用技能，扣除蓝量，计算cd等
        fightAccount.useSkill(skillResource);

        //真正造成伤害
        causeDamage(fightAccount, targetAccount, skillResource);

        targetNum = 3;
        // 多目标技能攻击周围角色
        if (targetNum > 1) {
            List<FightAccount> aroundFightAccounts = mapInfo.getAroundFightAccount(fightAccount, skillResource.getRange());
            int size = targetNum - 1;
            if (aroundFightAccounts.size() > 1) {
                for (FightAccount account : aroundFightAccounts) {
                    if (account.getAccountId().equals(targetAccount.getAccountId())) {
                        continue;
                    }
                    causeDamage(fightAccount, account, skillResource);
                    size--;
                    if (size == 0) {
                        break;
                    }
                }
            }
        }

    }

    public void useSkill(Account account, String targetAccountId, int skillId) {
        SpringContext.getSceneExecutorService().submit(FightUseSkillCommand.valueOf(account, targetAccountId, skillId));
    }


    /**
     * 目标是否在攻击范围内
     *
     * @param fightAccount
     * @param mapId
     * @param targetAccount
     * @param range
     * @return
     */
    public boolean isCanAttackTarget(FightAccount fightAccount, int mapId, FightAccount targetAccount, int range) {

        if (targetAccount == null) {
            I18Utils.notifyMessage(fightAccount.getAccountId(), I18nId.TARGET_NOT_IN_RANGE);
            return false;
        }
        Grid grid = fightAccount.getGrid();
        Grid targetGrid = targetAccount.getGrid();

        if (!grid.isInRange(targetGrid, range)) {
            I18Utils.notifyMessage(fightAccount.getAccountId(), I18nId.TARGET_NOT_IN_RANGE);
            return false;
        }

        return true;
    }

    /**
     * 造成战斗伤害
     *
     * @param attacker      攻击者
     * @param target        目标
     * @param skillResource 技能
     */
    public void causeDamage(FightAccount attacker, FightAccount target, SkillResource skillResource) {

        long attackerAtk = attacker.getAtk();
        long targetDef = target.getDef();

        long finalDamage = Math.max(1, (attackerAtk - targetDef) * (skillResource.getDamageRate() / GlobalConstant.RATIO_VALUE));
        target.setHp(Math.max(0, target.getHp() - finalDamage));
        long result = (target.getHp() - finalDamage) > 0 ? finalDamage : target.getHp();
        if (result == 0) {
            I18Utils.notifyMessage(attacker.getAccountId(), I18nId.TARGET_ALREADY_DEATH);
        } else {

            SM_Hit packet = SM_Hit.valueOf(attacker.getAccountId(), skillResource.getName(),
                    result, target.getHp(), target.getMaxHP());
            PacketSendUtil.send(target.getAccountId(), packet);

            SM_Attacker atkPacket = SM_Attacker.valueOf(target.getAccountId(), skillResource.getId(), skillResource.getName(), result);
            PacketSendUtil.send(attacker.getAccountId(), atkPacket);
            if (skillResource.getBuff() != 0) {
                AbstractBuff abstractBuff = SpringContext.getBuffService().createBuff(skillResource.getBuff());
                abstractBuff.setCreateTime(TimeUtil.now());
                target.addBuff(abstractBuff.getBuffId(), abstractBuff);
            }
        }
    }
}
