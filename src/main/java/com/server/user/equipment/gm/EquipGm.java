package com.server.user.equipment.gm;

import com.SpringContext;
import com.server.gm.anno.GmAnno;
import com.server.gm.anno.GmMethod;
import com.server.user.account.model.Account;
import com.server.user.equipment.packet.CM_Equip;
import com.server.user.equipment.packet.CM_EquipmentInfo;
import com.server.user.equipment.packet.CM_UnEquip;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/5/31 18:05
 */
@Component
@GmAnno(title = "装备gm")
public class EquipGm {

    @GmMethod(name = "打印装备信息", clz = CM_EquipmentInfo.class)
    public void printEquipments(Account account) {
        SpringContext.getEquipmentService().printEquipments(account);
    }

    @GmMethod(name = "穿戴装备", param = "参数:唯一id", clz = CM_Equip.class)
    public void equip(Account account, long identifyId) {
        SpringContext.getEquipmentService().equip(account, identifyId);
    }

    @GmMethod(name = "卸下装备", param = "参数:穿戴部位", clz = CM_UnEquip.class)
    public void unEquip(Account account, int index) {
        SpringContext.getEquipmentService().unEquip(account, index);
    }


}
