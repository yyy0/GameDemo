package com.server.user.fight.service;

import com.SpringContext;
import com.server.map.handler.AbstractMapHandler;
import com.server.map.model.Grid;
import com.server.map.model.MapInfo;
import com.server.map.resource.MapResource;
import com.server.monster.model.Monster;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;
import com.server.user.attribute.constant.GlobalConstant;
import com.server.user.buff.model.AbstractBuff;
import com.server.user.fight.FightAccount;
import com.server.user.fight.command.FightAtkMonsterCommand;
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
        Account acc = SpringContext.getAccountService().getAccount(fightAccount.getAccountId());
        MapInfo mapInfo = SpringContext.getWorldService().getMapInfo(acc, mapId);
        FightAccount targetAccount = mapInfo.getFightAccount(targetAccountId);
        if (targetAccount == null) {
            I18Utils.notifyMessageThrow(fightAccount.getAccountId(), I18nId.TARGET_NOT_IN_RANGE);
            return;
        }
        //是否在攻击范围内
        if (!isCanAttackTarget(fightAccount, targetAccount, skillResource.getRange())) {
            return;
        }
        //真正使用技能，扣除蓝量，计算cd等
        fightAccount.useSkill(skillResource);

        //真正造成伤害
        causeDamage(fightAccount, targetAccount, skillResource);

        // 多目标技能攻击周围角色
        if (targetNum > 1) {
            List<FightAccount> aroundFightAccounts = mapInfo.getAroundFightAccount(fightAccount, skillResource.getRange(), targetNum - 1);
            if (aroundFightAccounts.size() > 0) {
                for (FightAccount account : aroundFightAccounts) {
                    if (account.getAccountId().equals(targetAccountId)) {
                        continue;
                    }
                    causeDamage(fightAccount, account, skillResource);
                }
            }
        }

    }

    public void useSkill(Account account, String targetAccountId, int skillId) {
        SpringContext.getSceneExecutorService().submit(FightUseSkillCommand.valueOf(account, targetAccountId, skillId));
    }

    public void atkMonster(Account account, long monsterGid, int skillId) {
        SpringContext.getSceneExecutorService().submit(FightAtkMonsterCommand.valueOf(account, skillId, monsterGid));
    }

    public void doAtkMonster(FightAccount fightAccount, int skillId, long monsterGid, int mapId) {
        //主角是否能释放技能（是否有该技能, 蓝量, cd等条件够不够）
        if (!fightAccount.isCanUseSkill(skillId)) {
            return;
        }
        SkillResource skillResource = SpringContext.getSkillService().getSkillResource(skillId);

        MapResource resource = SpringContext.getWorldService().getMapResource(mapId);
        AbstractMapHandler mapHandler = AbstractMapHandler.getMapHandler(resource.getType());

        Account account = SpringContext.getAccountService().getAccount(fightAccount.getAccountId());
        MapInfo mapInfo = mapHandler.getMapInfo(account, 0);
        Monster monster = mapInfo.getMonsterByGid(monsterGid);
        if (monster == null) {
            I18Utils.notifyMessageThrow(fightAccount.getAccountId(), I18nId.TARGET_NOT_IN_RANGE);
            return;
        }

        // 判断怪物是否在攻击范围内
        Grid monsterGrid = monster.getGrid();
        Grid accountGrid = fightAccount.getGrid();
        if (!accountGrid.isInRange(monsterGrid, skillResource.getRange())) {
            I18Utils.notifyMessage(fightAccount.getAccountId(), I18nId.TARGET_NOT_IN_RANGE);
            return;
        }

        fightAccount.useSkill(skillResource);
        causeMonsterDamage(fightAccount, monster, skillResource);
        if (monster.isDead()) {
            mapHandler.handleDeadMonster(account, monster);
        }

        //多目标攻击怪物
        if (skillResource.getTargetNum() > 1) {
            List<Monster> monsters = mapInfo.getAroundMonster(fightAccount, skillResource.getRange(), skillResource.getTargetNum() - 1, monsterGid);
            if (monsters.size() > 0) {
                for (Monster monsterTemp : monsters) {
                    causeMonsterDamage(fightAccount, monsterTemp, skillResource);
                    if (monsterTemp.isDead()) {
                        mapHandler.handleDeadMonster(account, monster);
                    }
                }
            }
        }

    }


    /**
     * 目标是否在攻击范围内
     *
     * @param fightAccount
     * @param targetAccount
     * @param range
     * @return
     */
    public boolean isCanAttackTarget(FightAccount fightAccount, FightAccount targetAccount, int range) {

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

                target.addBuff(abstractBuff.getBuffId(), abstractBuff);
            }
        }
    }

    /**
     * 对怪物造成伤害
     *
     * @param attacker
     * @param target
     * @param skillResource
     */
    public void causeMonsterDamage(FightAccount attacker, Monster target, SkillResource skillResource) {

        long attackerAtk = attacker.getAtk();
        long targetDef = target.getDef();

        long finalDamage = Math.max(1, (attackerAtk - targetDef) * (skillResource.getDamageRate() / GlobalConstant.RATIO_VALUE));

        long result = (target.getCurHp() - finalDamage) > 0 ? finalDamage : target.getCurHp();
        target.setCurHp(target.getCurHp() - result);
        if (result == 0) {
            I18Utils.notifyMessage(attacker.getAccountId(), I18nId.TARGET_ALREADY_DEATH);
        } else {

            SM_Attacker atkPacket = SM_Attacker.valueOf(target.getName(), skillResource.getId(), skillResource.getName(), result);
            PacketSendUtil.send(attacker.getAccountId(), atkPacket);
            if (skillResource.getBuff() != 0) {
                AbstractBuff abstractBuff = SpringContext.getBuffService().createBuff(skillResource.getBuff());
                target.addBuff(abstractBuff.getBuffId(), abstractBuff);
            }
        }

    }
}
