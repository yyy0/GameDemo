package com.server.user.item.entity;

import com.server.tool.JsonUtils;
import com.server.user.item.constant.StorageConstant;
import com.server.user.item.storage.ItemStorage;

import javax.persistence.*;

/**
 * 背包
 *
 * @author yuxianming
 * @date 2019/5/13 21:52
 */
@Entity
@Table(name = "item_storage")
public class ItemStorageEnt {
    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号名称'", nullable = false)
    private String accountId;

    @Transient
    private ItemStorage itemStorage;

    @Lob
    @Column(columnDefinition = "blob comment '账号背包数据'")
    private byte[] StorageData;

    public static ItemStorageEnt valueOf(String accountId) {
        ItemStorageEnt storageEnt = new ItemStorageEnt();
        storageEnt.accountId = accountId;
        ItemStorage storage = ItemStorage.valueOf();
        int maxSize = StorageConstant.BAG_MAXSIZE;
        storage.ensureCapacity(maxSize);
        storageEnt.setItemStorage(storage);
        return storageEnt;
    }

    public boolean doSerialize() {
        this.StorageData = JsonUtils.objToByte(itemStorage);
        return true;
    }

    public boolean doDeserialize() {
        this.itemStorage = JsonUtils.byteToObj(StorageData, ItemStorage.class);
        int maxSize = StorageConstant.BAG_MAXSIZE;
        this.itemStorage.ensureCapacity(maxSize);
        return true;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public ItemStorage getItemStorage() {
        return itemStorage;
    }

    public void setItemStorage(ItemStorage itemStorage) {
        this.itemStorage = itemStorage;
    }

    public byte[] getStorageData() {
        return StorageData;
    }

    public void setStorageData(byte[] storageData) {
        StorageData = storageData;
    }
}
