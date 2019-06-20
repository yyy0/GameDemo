package com.server.user.fight;

import com.SpringContext;
import com.server.map.model.Grid;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.tool.MapUtils;
import com.server.tool.TimeUtil;
import com.server.user.account.model.Account;
import com.server.user.attribute.AttributeUtil;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.Attribute;
import com.server.user.buff.model.AbstractBuff;
import com.server.user.skill.model.AccountSkill;
import com.server.user.skill.resource.SkillResource;

import java.util.HashMap;
import java.util.Map;

/**
 * 战斗账号
 *
 * @author yuxianming
 * @date 2019/6/14 10:51
 */
public class FightAccount {

    private String accountId;

    private Grid grid;

    private AccountSkill accountSkill;

    /**
     * 属性
     */
    private Map<AttributeType, Attribute> attributeMap = new HashMap<>();

    /**
     * 技能cd
     */
    private Map<Integer, Long> skillCd = new HashMap<>();

    /**
     * buff 列表
     */
    private Map<Integer, AbstractBuff> buffMap = new HashMap<>();

    /**
     * 当前血量
     */
    private long hp;

    /**
     * 当前蓝量
     */
    private long mp;

    public static FightAccount valueOf(Account account) {
        FightAccount fightAccount = new FightAccount();
        fightAccount.accountId = account.getAccountId();
        fightAccount.grid = account.getGrid();
        fightAccount.attributeMap = account.getCurCopyAttribute();
        fightAccount.accountSkill = account.getAccountSkill().copy();
        fightAccount.hp = fightAccount.attributeMap.get(AttributeType.MAX_HP).getValue();
        fightAccount.mp = fightAccount.attributeMap.get(AttributeType.MAGIC).getValue();
        return fightAccount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Map<AttributeType, Attribute> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<AttributeType, Attribute> attributeMap) {
        this.attributeMap = attributeMap;
        Attribute hp = attributeMap.get(AttributeType.MAX_HP);
        if (hp.getValue() < this.hp) {
            this.hp = hp.getValue();
        }
    }

    public Map<Integer, Long> getSkillCd() {
        return skillCd;
    }

    public void setSkillCd(Map<Integer, Long> skillCd) {
        this.skillCd = skillCd;
    }

    public AccountSkill getAccountSkill() {
        return accountSkill;
    }

    public void setAccountSkill(AccountSkill accountSkill) {
        this.accountSkill = accountSkill;
    }

    /**
     * 验证战斗账号是否能使用技能
     */
    public boolean isCanUseSkill(int skillId) {
        // 技能栏是否有技能
        if (!accountSkill.isSlotHasSkill(skillId)) {
            I18Utils.notifyMessage(accountId, I18nId.ACCOUNT_NULL_SKILL);
            return false;
        }

        SkillResource skillResource = SpringContext.getSkillService().getSkillResource(skillId);

        //判断cd

        if (null != skillCd.get(skillId)) {
            long lastUseTime = skillCd.get(skillId);
            long now = TimeUtil.now();
            long gap = now - lastUseTime;
            long cd = skillResource.getCd();
            if (gap < cd) {
                I18Utils.notifyMessage(accountId, I18nId.SKILL_CD_NOW);
                return false;
            }
        }

        //判断蓝量
        if (mp < skillResource.getNeedMp()) {
            I18Utils.notifyMessage(accountId, I18nId.NO_MP);
            return false;
        }
        return true;
    }


    public void useSkill(SkillResource skillResource) {
        skillCd.put(skillResource.getId(), TimeUtil.now());
        this.mp -= skillResource.getNeedMp();
    }

    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public long getMp() {
        return mp;
    }

    public void setMp(long mp) {
        this.mp = mp;
    }

    public long getAtk() {
        Attribute atk = attributeMap.get(AttributeType.ATTACK);
        if (atk == null) {
            return 0;
        }
        return Math.max(0, atk.getValue());
    }

    public long getDef() {
        Attribute def = attributeMap.get(AttributeType.DEFENCE);
        if (def == null) {
            return 0;
        }
        return Math.max(0, def.getValue());
    }

    public long getMaxHP() {
        Attribute maxHP = attributeMap.get(AttributeType.MAX_HP);
        if (maxHP == null) {
            return 0;
        }
        return Math.max(0, maxHP.getValue());
    }

    public FightAccount copy() {
        FightAccount fightAccount = new FightAccount();
        fightAccount.hp = this.hp;
        fightAccount.mp = this.mp;
        fightAccount.accountId = this.accountId;
        fightAccount.skillCd = MapUtils.deepCopy(this.skillCd);
        fightAccount.grid = Grid.valueOf(this.grid.getX(), this.grid.getY());
        fightAccount.accountSkill = this.accountSkill.copy();
        fightAccount.attributeMap = AttributeUtil.copy(this.attributeMap);
        return fightAccount;
    }

    public void removeBuff(int buffId) {
        buffMap.remove(buffId);
    }

    public void addBuff(int buffId, AbstractBuff buff) {
        buffMap.put(buffId, buff);
        long now = TimeUtil.now();
        buff.setCreateTime(now);
        buff.setLastEffectTime(now);
        buff.doAction(this);
    }

    public AbstractBuff getBuff(int buffId) {
        return buffMap.get(buffId);
    }

    public boolean isHasBuff() {
        return buffMap.size() != 0;
    }

    public void handleBuff() {
        long now = TimeUtil.now();
        for (AbstractBuff abstractBuff : buffMap.values()) {
            if (abstractBuff.isExpire(now)) {
                this.removeBuff(abstractBuff.getBuffId());
            }
            if (abstractBuff.isCanAction(now)) {
                abstractBuff.setLastEffectTime(abstractBuff.getLastEffectTime() + abstractBuff.getPeriod());
                abstractBuff.doAction(this);
            }
        }
    }
}
