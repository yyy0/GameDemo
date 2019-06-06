package com.server.user.equipment.facade;

import com.SpringContext;
import com.server.dispatcher.HandlerAnno;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import com.server.user.equipment.packet.CM_Equip;
import com.server.user.equipment.packet.CM_EquipmentInfo;
import com.server.user.equipment.packet.CM_UnEquip;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/6 11:17
 */
@Component
public class EquipmentFacade {

    /**
     * 打印装备信息
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void printEquipments(TSession session, CM_EquipmentInfo req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getEquipmentService().printEquipments(account);
    }

    /**
     * 穿戴装备
     */
    @HandlerAnno
    public void equip(TSession session, CM_Equip req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getEquipmentService().equip(account, req.getGid());
    }

    /**
     * 脱装备
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void unEquip(TSession session, CM_UnEquip req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getEquipmentService().unEquip(account, req.getIndex());
    }


}
