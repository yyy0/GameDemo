package com.server.user.item.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 仓库
 *
 * @author yuxianming
 * @date 2019/5/13 21:53
 */
@Entity
@Table(name = "item_warehouse")
public class WarehouseEnt {

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号名称'", nullable = false)
    private String accountId;
}
