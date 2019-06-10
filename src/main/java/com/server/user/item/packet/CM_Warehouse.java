package com.server.user.item.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:07
 * 打印仓库信息
 */
public class CM_Warehouse implements Serializable {

    public static CM_Warehouse valueOf() {
        CM_Warehouse packet = new CM_Warehouse();
        return packet;
    }
}
