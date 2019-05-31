package com.server.user.item.storage;

import com.server.user.item.constant.StorageConstant;
import com.server.user.item.model.AbstractItem;

/**
 * 仓库
 *
 * @author yuxianming
 * @date 2019/5/13 17:08
 */
public class Warehouse extends ItemStorage {

    public static Warehouse valueOf() {
        int size = StorageConstant.WAREHOUSE_MAXSIZE;
        return new Warehouse(size);
    }

    public Warehouse() {
    }

    public Warehouse(int size) {
        this.setSize(size);
        this.setItems(new AbstractItem[size]);
    }


}
