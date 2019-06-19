package com.server.user.item.gm;

import com.SpringContext;
import com.server.gm.anno.GmAnno;
import com.server.gm.anno.GmMethod;
import com.server.user.account.model.Account;
import com.server.user.item.model.AbstractItem;
import com.server.user.item.packet.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yuxianming
 * @date 2019/5/28 18:10
 */
@Component
@GmAnno(title = "道具GM")
public class ItemGm {

    @GmMethod(name = "添加道具", param = "参数:道具id 数量", clz = CM_AddItem.class)
    public void addItem(Account account, int itemId, int num) {
        List<AbstractItem> items = SpringContext.getStoreService().createItems(itemId, num);
        SpringContext.getStoreService().addItemsToBag(account, items);
        printBag(account);
    }

    @GmMethod(name = "清空背包", clz = CM_ClearBag.class)
    public void clearBag(Account account) {
        SpringContext.getStoreService().clearBag(account);
        printBag(account);
    }

    @GmMethod(name = "清空仓库", clz = CM_ClearStorage.class)
    public void clearStorage(Account account) {
        SpringContext.getStoreService().clearWarehouse(account);
        printWarehouse(account);
    }

    @GmMethod(name = "打印背包信息", clz = CM_BagInfo.class)
    public void printBag(Account account) {
        SpringContext.getStoreService().printItems(account.getAccountId());
    }

    @GmMethod(name = "打印仓库信息", clz = CM_Warehouse.class)
    public void printWarehouse(Account account) {
        SpringContext.getStoreService().printWareItems(account.getAccountId());
    }

    @GmMethod(name = "背包至仓库", param = "参数:唯一id", clz = CM_MoveItemToWare.class)
    public void moveItemBagToWarehouse(Account account, long id) {
        SpringContext.getStoreService().moveBagToWarehouse(account, id);
        printWarehouse(account);
    }

    @GmMethod(name = "仓库至背包", param = "参数:唯一id", clz = CM_MoveItemWareToBag.class)
    public void moveWarehouseToBag(Account account, long id) {
        SpringContext.getStoreService().moveWarehouseToBag(account, id);
        printBag(account);
    }
}
