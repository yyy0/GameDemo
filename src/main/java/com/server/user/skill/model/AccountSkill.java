package com.server.user.skill.model;

import com.server.user.skill.constant.SkillConstant;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色技能信息
 *
 * @author yuxianming
 * @date 2019/6/11 23:32
 */
public class AccountSkill {

    private String accountId;

    /**
     * 所有技能
     */
    private Set<Integer> allSkill = new HashSet<>();

    /**
     * 技能槽信息
     */
    private int[] skillSlot;


    public static AccountSkill valueOf(String accountId) {
        AccountSkill accountSkill = new AccountSkill();
        accountSkill.accountId = accountId;
        accountSkill.skillSlot = new int[SkillConstant.SKILL_SLOT_NUM];
        return accountSkill;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Set<Integer> getAllSkill() {
        return allSkill;
    }

    public void setAllSkill(Set<Integer> allSkill) {
        this.allSkill = allSkill;
    }

    public int[] getSkillSlot() {
        return skillSlot;
    }

    public void setSkillSlot(int[] skillSlot) {
        this.skillSlot = skillSlot;
    }

    /**
     * 删除某个技能 同时删除技能库与技能槽
     *
     * @param skillId
     */
    public void removeSkill(int skillId) {
        allSkill.remove(skillId);
        for (int i = 0; i < skillSlot.length; i++) {
            if (skillSlot[i] == skillId) {
                skillSlot[i] = 0;
                break;
            }
        }
    }

    /**
     * 移除技能栏的某个技能
     *
     * @param index
     */
    public void removeSkillFromSlot(int index) {
        skillSlot[index] = 0;
    }

    /**
     * 将某个技能设置在技能栏中
     *
     * @param skillId
     */
    public boolean setSkillToSlot(int skillId, int index) {
        if (isAllHasSkill(skillId) && !isSlotHasSkill(skillId)) {
            skillSlot[index] = skillId;
            return true;
        }
        return false;
    }

    /**
     * 是否拥有某技能
     *
     * @param skillId
     * @return
     */
    public boolean isAllHasSkill(int skillId) {
        return allSkill.contains(skillId);
    }

    /**
     * 技能栏是否拥有某技能
     *
     * @param skillId
     * @return
     */
    public boolean isSlotHasSkill(int skillId) {
        for (int i = 0; i < skillSlot.length; i++) {
            if (skillSlot[i] == skillId) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找技能栏某部位的技能id
     *
     * @param index
     * @return
     */
    public int findSlotSkillByIndex(int index) {
        return skillSlot[index];
    }


    public void addSkill(int skillId) {
        allSkill.add(skillId);
    }


    public AccountSkill copy() {
        AccountSkill accountSkill = new AccountSkill();
        accountSkill.accountId = this.accountId;

        Set<Integer> skills = new HashSet<>(this.allSkill.size());
        skills.addAll(this.allSkill);

        int[] skillSlot = new int[this.skillSlot.length];
        System.arraycopy(this.skillSlot, 0, skillSlot, 0, skillSlot.length);

        accountSkill.allSkill = skills;
        accountSkill.skillSlot = skillSlot;
        return accountSkill;
    }
}
