package com.server.user.equipment.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:18
 * 打印装备信息
 */
public class CM_EquipmentInfo implements Serializable {

    public static CM_EquipmentInfo valueOf() {
        CM_EquipmentInfo packet = new CM_EquipmentInfo();
        return packet;
    }
}
