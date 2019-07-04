package com.server.publicsystem.guild.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/**
 * @author yuxianming
 * @date 2019/7/4 17:16
 */
@Entity
@Table(name = "guild")
public class GuildEnt {

    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '工会名称'", nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '会长id'", nullable = false)
    private String leader;

    @Column(columnDefinition = "timestamp comment '工会创建时间'")
    private Date createTime;


    private Set<String> members;
}
