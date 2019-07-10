package com.server.publicsystem.guild.entity;

import com.server.publicsystem.guild.model.GuildInfo;
import com.server.tool.JsonUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yuxianming
 * @date 2019/7/4 17:16
 */
@Entity
@Table(name = "guild")
@NamedQueries(value = {
        @NamedQuery(name = "loadAllGuild", query = "select g from GuildEnt g order by g.id")
})
public class GuildEnt {

    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '工会名称'", nullable = false)
    private String name;

    @Column(columnDefinition = "timestamp comment '工会创建时间'")
    private Date createTime;

    @Transient
    private transient GuildInfo guildInfo;

    @Lob
    @Column(columnDefinition = "blob comment '账号基本数据'")
    private byte[] guildData;

    public void doSerialize() {
        this.guildData = JsonUtils.objToByte(this.guildInfo);
    }

    public void doSerialize(GuildInfo guildInfo) {
        this.guildInfo = guildInfo;
        this.guildData = JsonUtils.objToByte(guildInfo);
    }


    public void doDeserialize() {
        this.guildInfo = JsonUtils.byteToObj(this.guildData, GuildInfo.class);
    }

    public static GuildEnt valueOf(long guildId, String name, String leader) {
        GuildEnt ent = new GuildEnt();
        ent.id = guildId;
        ent.name = name;
        ent.createTime = new Date();
        ent.guildInfo = GuildInfo.valueOf(guildId, name, leader);
        ent.doSerialize();
        return ent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public GuildInfo getGuildInfo() {
        return guildInfo;
    }

    public void setGuildInfo(GuildInfo guildInfo) {
        this.guildInfo = guildInfo;
    }

    public byte[] getGuildData() {
        return guildData;
    }

    public void setGuildData(byte[] guildData) {
        this.guildData = guildData;
    }
}
