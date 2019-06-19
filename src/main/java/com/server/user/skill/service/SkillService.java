package com.server.user.skill.service;

import com.SpringContext;
import com.server.common.entity.CommonEntManager;
import com.server.common.resource.ResourceManager;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;
import com.server.user.fight.syncStrategy.SkillSyncStrategy;
import com.server.user.skill.entity.SkillEnt;
import com.server.user.skill.model.AccountSkill;
import com.server.user.skill.packet.SM_AllSkill;
import com.server.user.skill.packet.SM_SlotSkill;
import com.server.user.skill.resource.SkillResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author yuxianming
 * @date 2019/6/12 11:36
 */
@Component
public class SkillService {

    @Autowired
    private ResourceManager resourceManager;

    @Autowired
    private CommonEntManager<String, SkillEnt> commonEntManager;

    public static Logger logger = LoggerFactory.getLogger(SkillService.class);

    /**
     * 学习技能
     *
     * @param account
     * @param skillId
     */
    public void studySkill(Account account, int skillId) {

        //获取skillResource 判断是否找到resource
        SkillResource skillResource = getSkillResource(skillId);
        if (skillResource == null) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }
        AccountSkill accountSkill = getAccountSkill(account.getAccountId());
        //判断玩家是否已有该技能
        if (accountSkill.isAllHasSkill(skillId)) {
            I18Utils.notifyMessageThrow(account, I18nId.SKILL_ALREADY_ACTIVE);
        }

        // 扣除消耗
        Map<Integer, Integer> itemsMap = skillResource.getItem();
        SpringContext.getStoreService().isEnoughItemThrow(account, itemsMap);
        SpringContext.getStoreService().reduceBagItemThrow(account, itemsMap);

        accountSkill.addSkill(skillId);
        saveSkillData(account.getAccountId(), accountSkill);
        account.fightSync(new SkillSyncStrategy());
        printAllSkill(account);

    }

    /**
     * 打印已学所有技能
     *
     * @param account
     */
    public void printAllSkill(Account account) {
        AccountSkill accountSkill = getAccountSkill(account.getAccountId());

        Set<Integer> allSkill = accountSkill.getAllSkill();
        if (allSkill.size() == 0) {
            I18Utils.notifyMessage(account, I18nId.ACCOUNT_NULL_SKILL);
            return;
        }
        PacketSendUtil.send(account, SM_AllSkill.valueOf(accountSkill.getAllSkill()));

    }

    /**
     * 打印技能栏技能
     */
    public void printSkillSlot(Account account) {
        AccountSkill accountSkill = getAccountSkill(account.getAccountId());
        PacketSendUtil.send(account, SM_SlotSkill.valueOf(accountSkill.getSkillSlot()));

    }

    public void removeSkillFromSlot(Account account, int index) {

        AccountSkill accountSkill = getAccountSkill(account.getAccountId());
        int skillId = accountSkill.findSlotSkillByIndex(index);
        if (skillId == 0) {
            I18Utils.notifyMessageThrow(account, I18nId.ACCOUNT_NULL_SKILL);
        }
        accountSkill.removeSkillFromSlot(index);
        saveSkillData(account.getAccountId(), accountSkill);
        account.fightSync(new SkillSyncStrategy());

    }

    /**
     * 设置某个技能至技能栏
     *
     * @param account
     * @param skillId
     * @param index
     */
    public void setSkillToSlot(Account account, int skillId, int index) {
        //获取skillResource 判断是否找到resource
        SkillResource skillResource = getSkillResource(skillId);
        if (skillResource == null) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }
        AccountSkill accountSkill = getAccountSkill(account.getAccountId());
        //判断玩家是否已有该技能
        if (!accountSkill.isAllHasSkill(skillId)) {
            I18Utils.notifyMessageThrow(account, I18nId.SKILL_NOT_ACTIVE);
        }

        accountSkill.setSkillToSlot(skillId, index);
        saveSkillData(account.getAccountId(), accountSkill);
        //同步技能至 战斗账号
        account.fightSync(new SkillSyncStrategy());
        printSkillSlot(account);

    }

    public SkillResource getSkillResource(int skillId) {
        Map<Integer, Object> resources = resourceManager.getResources(SkillResource.class.getSimpleName());
        SkillResource skillResource = (SkillResource) resources.get(skillId);
        if (skillResource == null) {
            logger.error("SkillResource找不到对应配置id：{0}" + skillId);
        }
        return skillResource;
    }

    public SkillEnt getSkillEnt(String accountId) {
        SkillEnt ent = commonEntManager.getEnt(SkillEnt.class, accountId);

        if (ent == null) {
            ent = SkillEnt.valueOf(accountId);
            commonEntManager.createEnt(ent);
        }
        return ent;
    }

    public AccountSkill getAccountSkill(String accountId) {
        SkillEnt ent = getSkillEnt(accountId);
        ent.doDeserialize();
        return ent.getAccountSkill();
    }

    public void saveSkillData(String accountId, AccountSkill accountSkill) {
        SkillEnt ent = getSkillEnt(accountId);
        ent.doSerialize(accountSkill);
        commonEntManager.update();
    }
}
