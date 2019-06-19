package com.server.user.skill.entity;

import com.server.tool.JsonUtils;
import com.server.user.skill.model.AccountSkill;

import javax.persistence.*;

/**
 * @author yuxianming
 * @date 2019/6/11 22:49
 */
@Entity
@Table(name = "account_skill")
public class SkillEnt {

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号名称'", nullable = false)
    private String accountId;

    @Transient
    private AccountSkill accountSkill;

    @Lob
    @Column(columnDefinition = "blob comment '账号技能数据'")
    private byte[] skillData;

    public static SkillEnt valueOf(String accountId) {
        SkillEnt ent = new SkillEnt();
        ent.accountId = accountId;
        ent.accountSkill = AccountSkill.valueOf(accountId);
        ent.doSerialize();
        return ent;
    }

    public void doSerialize(AccountSkill accountSkill) {
        this.skillData = JsonUtils.objToByte(accountSkill);
    }

    public void doSerialize() {
        this.skillData = JsonUtils.objToByte(accountSkill);
    }

    public void doDeserialize() {
        this.accountSkill = JsonUtils.byteToObj(skillData, AccountSkill.class);

    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public AccountSkill getAccountSkill() {
        return accountSkill;
    }

    public void setAccountSkill(AccountSkill accountSkill) {
        this.accountSkill = accountSkill;
    }

    public byte[] getSkillData() {
        return skillData;
    }

    public void setSkillData(byte[] skillData) {
        this.skillData = skillData;
    }
}
