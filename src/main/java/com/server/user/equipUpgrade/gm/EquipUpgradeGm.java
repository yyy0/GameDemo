package com.server.user.equipUpgrade.gm;

import com.SpringContext;
import com.server.command.anno.GmAnno;
import com.server.command.anno.GmMethod;
import com.server.user.account.model.Account;
import com.server.user.equipUpgrade.packet.CM_EquipUpGrade;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/6 9:32
 */
@Component
@GmAnno(title = "装备升阶gm")
public class EquipUpgradeGm {

    @GmMethod(name = "装备升阶", param = "参数:装备部位", clz = CM_EquipUpGrade.class)
    public void equipUpgrade(Account account, int position) {
        SpringContext.getEquipUpgradeService().equipUpgrade(account, position);
        SpringContext.getEquipmentService().printEquipments(account);

    }
}
