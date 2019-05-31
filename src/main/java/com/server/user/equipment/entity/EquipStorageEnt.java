package com.server.user.equipment.entity;

import com.server.tool.JsonUtils;
import com.server.user.equipment.model.EquipStorage;

import javax.persistence.*;

/**
 * @author yuxianming
 * @date 2019/5/31 11:24
 */
@Entity
@Table(name = "equipstorage")
public class EquipStorageEnt {

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号名称'", nullable = false)
    private String accountId;

    @Transient
    private EquipStorage equipStorage;

    @Lob
    @Column(columnDefinition = "blob comment '账号装备数据'")
    private byte[] equipStorageData;

    public static EquipStorageEnt valueOf(String accountId) {
        EquipStorageEnt equipStorageEnt = new EquipStorageEnt();
        equipStorageEnt.accountId = accountId;
        return equipStorageEnt;
    }

    public void doSerialize(EquipStorage equipStorage) {
        this.equipStorageData = JsonUtils.objToByte(equipStorage);
    }

    public void doSerialize() {
        this.equipStorageData = JsonUtils.objToByte(equipStorage);
    }

    public void doDeserialize() {
        this.equipStorage = JsonUtils.byteToObj(equipStorageData, EquipStorage.class);

    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public EquipStorage getEquipStorage() {
        return equipStorage;
    }

    public void setEquipStorage(EquipStorage equipStorage) {
        this.equipStorage = equipStorage;
    }

    public byte[] getEquipStorageData() {
        return equipStorageData;
    }

    public void setEquipStorageData(byte[] equipStorageData) {
        this.equipStorageData = equipStorageData;
    }
}
