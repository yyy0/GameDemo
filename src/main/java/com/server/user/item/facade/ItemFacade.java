package com.server.user.item.facade;

import com.SpringContext;
import com.server.dispatcher.HandlerAnno;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import com.server.user.item.model.AbstractItem;
import com.server.user.item.packet.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yuxianming
 * @date 2019/6/6 10:54
 */
@Component
public class ItemFacade {

    /**
     * 添加道具
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void addItem(TSession session, CM_AddItem req) {
        Account account = SessionUtil.getAccountBySession(session);
        List<AbstractItem> items = SpringContext.getStoreService().createItems(req.getItemModelId(), req.getNum());
        SpringContext.getStoreService().addItemsToBag(account, items);
        SpringContext.getStoreService().printItems(account.getAccountId());
    }

    /**
     * 清除背包
     *
     * @param session
     * @param packet
     */
    @HandlerAnno
    public void clearBag(TSession session, CM_ClearBag packet) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getStoreService().clearBag(account);
    }

    /**
     * 清除仓库
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void clearStorage(TSession session, CM_ClearStorage req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getStoreService().clearWarehouse(account);
    }

    /**
     * 打印背包信息
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void printBag(TSession session, CM_BagInfo req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getStoreService().printItems(account.getAccountId());
    }


    /**
     * 打印仓库信息
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void printWarehouse(TSession session, CM_Warehouse req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getStoreService().printWareItems(account.getAccountId());
    }

    /**
     * 移动道具从背包到仓库
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void moveItemBagToWarehouse(TSession session, CM_MoveItemToWare req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getStoreService().moveBagToWarehouse(account, req.getGid());
    }

    /**
     * 移动道具从仓库到背包
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void moveWarehouseToBag(TSession session, CM_MoveItemWareToBag req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getStoreService().moveWarehouseToBag(account, req.getGid());
    }
}
