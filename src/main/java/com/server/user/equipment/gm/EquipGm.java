package com.server.user.equipment.gm;

import com.SpringContext;
import com.server.command.anno.GmAnno;
import com.server.command.anno.GmMethod;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/5/31 18:05
 */
@Component
@GmAnno(title = "装备gm")
public class EquipGm {

    @GmMethod(name = "打印装备信息")
    public void printEquipments(Account account) {
        SpringContext.getEquipmentService().printEquipments(account);
    }

    @GmMethod(name = "穿戴装备", param = "参数:唯一id")
    public void equip(Account account, long identifyId) {
        SpringContext.getEquipmentService().equip(account, identifyId);
    }

    @GmMethod(name = "卸下装备", param = "参数:穿戴部位")
    public void unEquip(Account account, int index) {
        SpringContext.getEquipmentService().unEquip(account, index);
    }


}
