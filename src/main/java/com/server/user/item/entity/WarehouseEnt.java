package com.server.user.item.entity;

import com.server.tool.JsonUtils;
import com.server.user.item.constant.StorageConstant;
import com.server.user.item.storage.Warehouse;

import javax.persistence.*;

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

    @Transient
    private Warehouse warehouse;

    @Lob
    @Column(columnDefinition = "blob comment '账号仓库数据'")
    private byte[] warehouseData;

    public static WarehouseEnt valueOf(String accountId) {
        WarehouseEnt warehouseEnt = new WarehouseEnt();
        warehouseEnt.accountId = accountId;
        Warehouse warehouse = Warehouse.valueOf();
        int maxSize = StorageConstant.WAREHOUSE_MAXSIZE;
        warehouse.ensureCapacity(maxSize);
        warehouseEnt.setWarehouse(warehouse);
        warehouseEnt.doSerialize();
        return warehouseEnt;
    }

    public boolean doSerialize() {
        this.warehouseData = JsonUtils.objToByte(warehouse);
        return true;
    }

    public boolean doSerialize(Warehouse warehouse) {
        this.warehouseData = JsonUtils.objToByte(warehouse);
        return true;
    }

    public boolean doDeserialize() {
        this.warehouse = JsonUtils.byteToObj(warehouseData, Warehouse.class);
        int maxSize = StorageConstant.WAREHOUSE_MAXSIZE;
        this.warehouse.ensureCapacity(maxSize);
        return true;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public byte[] getWarehouseData() {
        return warehouseData;
    }

    public void setWarehouseData(byte[] warehouseData) {
        this.warehouseData = warehouseData;
    }
}
