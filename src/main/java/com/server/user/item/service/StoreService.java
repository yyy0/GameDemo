package com.server.user.item.service;

import com.server.user.account.model.Account;
import com.server.user.item.entity.ItemStorageEnt;
import com.server.user.item.model.AbstractItem;
import com.server.user.item.resource.ItemResource;
import com.server.user.item.storage.ItemStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yuxianming
 * @date 2019/5/13 17:33
 */
@Component
public class StoreService {

    @Autowired
    private ItemManager itemManager;

    private static Logger logger = LoggerFactory.getLogger(StoreService.class);


    /**
     * 剩余背包格子是否足够
     */
    public boolean isEnoughPackSize(Account account, List<AbstractItem> items) {

        ItemStorage itemStorage = getItemStorageEnt(account.getAccountId()).getItemStorage();
        int needSize = items.size();
        if (needSize > itemStorage.getEmptySize()) {
            logger.error("背包剩余格子不足");
            return false;
        }
        return true;
    }

    /**
     * 剩余背包格子是否足够
     */
    public boolean isEnoughPackSize(Account account, int itemModelId, int num) {

        int needSize = this.needSize(itemModelId, num);
        ItemStorage itemStorage = getItemStorage(account.getAccountId());
        if (needSize > itemStorage.getEmptySize()) {
            logger.error("背包剩余格子不足");
            return false;
        }
        return true;

    }

    /**
     * 计算存储道具所需格子数
     */
    public int needSize(int itemModelId, int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("道具数量必须大于0 ！！");
        }
        ItemResource itemResource = getItemResource(itemModelId);
        int overLimit = itemResource.getOverLimit();
        int size = num / overLimit + num % overLimit == 0 ? 0 : 1;
        return size;
    }

    /**
     * 生成一个道具
     */
    public AbstractItem createItem(int itemModelId) {
        ItemResource itemResource = getItemResource(itemModelId);
        return doCreateItem(itemResource, 1);
    }

    /**
     * 获取itemResource
     *
     * @param itemModelId
     * @return
     */
    public ItemResource getItemResource(int itemModelId) {
        ItemResource itemResource = itemManager.getItemResource(itemModelId);
        if (itemResource == null) {
            logger.error("找不到对应resource:" + itemModelId);
        }
        return itemResource;
    }

    /**
     * 创建道具
     *
     * @param itemResource
     * @param num
     * @return
     */
    private AbstractItem doCreateItem(ItemResource itemResource, int num) {
        AbstractItem item = itemResource.getItemType().create();
        item.setItemModelId(item.getItemModelId());
        item.setNum(num);
        return item;
    }

    /**
     * 添加一堆道具
     *
     * @param itemModelId
     * @param num
     * @return
     */
    public List<AbstractItem> createItems(int itemModelId, int num) {

        ItemResource itemResource = getItemResource(itemModelId);
        if (itemResource == null) {
            logger.error("找不到对应resource:" + itemModelId);
            return Collections.emptyList();
        }
        //道具叠加数量
        int overLimit = itemResource.getOverLimit();
        //实际占背包格子数
        int size = num / overLimit + num % overLimit == 0 ? 0 : 1;
        List<AbstractItem> result = new ArrayList<>();
        for (int i = 0; i < size - 1; i++) {
            result.add(doCreateItem(itemResource, overLimit));
            num -= overLimit;
        }
        result.add(doCreateItem(itemResource, num));
        return result;
    }

    /**
     * 添加道具至背包
     *
     * @param account
     * @param item
     */
    public void addItemToBag(Account account, AbstractItem item) {
        if (item == null) {
            return;
        }
        int num = item.getNum();
        if (!isEnoughPackSize(account, item.getItemModelId(), num)) {
            return;
        }
        ItemStorage itemStorage = getItemStorage(account.getAccountId());

    }

    /**
     * 打印背包物品信息
     */
    public void printItems(String accountId) {
        ItemStorageEnt itemStorageEnt = itemManager.getOrCreateItemStorageEnt(accountId);

        AbstractItem[] items = itemStorageEnt.getItemStorage().getItems();
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i]);
        }
    }

    public ItemStorageEnt getItemStorageEnt(String accountId) {
        return itemManager.getOrCreateItemStorageEnt(accountId);
    }

    public ItemStorage getItemStorage(String accountId) {
        return itemManager.getItemStorage(accountId);
    }

}
