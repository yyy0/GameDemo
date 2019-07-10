package com.server.publicsystem.guild.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yuxianming
 * @date 2019/7/6 16:32
 */
@Entity
@Table(name = "accountGuildEnt")
public class AccountGuildEnt {

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号id'", nullable = false)
    private String accountId;

    @Column(name = "guildId", nullable = false)
    private long guildId;

    @Column(name = "position", nullable = false)
    private int position;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '工会名称'")
    private String guildName;

    public static AccountGuildEnt valueOf(String accountId, long guildId, int position) {
        AccountGuildEnt ent = new AccountGuildEnt();
        ent.accountId = accountId;
        ent.guildId = guildId;
        ent.position = position;
        ent.setGuildName("");
        return ent;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void exitGuild() {
        this.setGuildName("");
        this.setPosition(0);
        this.setGuildId(0L);
    }

    public void copyEnt(AccountGuildEnt ent) {
        if (ent == null) {
            return;
        }
        if (ent.getAccountId().equals(this.accountId)) {
            this.guildId = ent.getGuildId();
            this.guildName = ent.getGuildName();
            this.position = ent.position;
        }
    }
}
