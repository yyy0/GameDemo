package com.server.user.item.service;

import com.SpringContext;
import com.server.common.entity.CommonEntManager;
import com.server.common.identity.service.IdentifyService;
import com.server.common.resource.ResourceManager;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;
import com.server.user.item.entity.ItemStorageEnt;
import com.server.user.item.entity.WarehouseEnt;
import com.server.user.item.model.AbstractItem;
import com.server.user.item.packet.SM_BagInfo;
import com.server.user.item.packet.SM_WareInfo;
import com.server.user.item.resource.ItemResource;
import com.server.user.item.storage.ItemStorage;
import com.server.user.item.storage.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/13 17:33
 */
@Component
public class StoreService {

    @Autowired
    private ResourceManager resourceManager;

    @Autowired
    private CommonEntManager<String, ItemStorageEnt> itemStorageManager;

    @Autowired
    private CommonEntManager<String, WarehouseEnt> warehouseManager;

    private static Logger logger = LoggerFactory.getLogger(StoreService.class);

    /**
     * 获取itemResource
     *
     * @param id 道具id
     * @return
     */
    public ItemResource getItemResource(int id) {
        Map<Integer, Object> itemResources = resourceManager.getResources(ItemResource.class.getSimpleName());
        ItemResource resource = (ItemResource) itemResources.get(id);
        if (resource == null) {
            logger.error("ItemResource找不到对应配置id：{0}" + id);
        }
        return resource;
    }

    private long createIdentifyId() {
        return SpringContext.getIdentifyService().getNextIdentify(IdentifyService.IdentifyType.ITEM);
    }


    /**
     * 剩余背包格子是否足够
     */
    public void isEnoughPackSizeThrow(Account account, int itemModelId, int num) {

        int needSize = this.needSize(itemModelId, num);
        ItemStorage itemStorage = getItemStorage(account.getAccountId());
        if (needSize > itemStorage.getEmptySize()) {
            PacketSendUtil.send(account, I18nId.BAG_NOT_ENOUGH);
            throw new RuntimeException("背包剩余格子不足");
        }

    }
    /**
     * 剩余背包格子是否足够
     */
    public boolean isEnoughPackSize(Account account, List<AbstractItem> items) {

        ItemStorage itemStorage = getItemStorage(account.getAccountId());
        int needSize = items.size();
        if (needSize > itemStorage.getEmptySize()) {
            I18Utils.notifyMessageThrow(account, I18nId.BAG_NOT_ENOUGH);
            logger.error("背包剩余格子不足");
            return false;
        }
        return true;
    }

    /**
     * 剩余背包格子是否足够 1格子
     */
    public boolean isEnoughPackSize(Account account) {

        ItemStorage itemStorage = getItemStorage(account.getAccountId());
        if (1 > itemStorage.getEmptySize()) {
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
     * 剩余仓库格子是否足够
     */
    public boolean isEnoughWarehouseSize(Account account, int num) {

        Warehouse warehouse = getWarehouse(account.getAccountId());
        if (num > warehouse.getEmptySize()) {
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
     * 创建道具
     *
     * @param itemResource
     * @param num
     * @return
     */
    private AbstractItem doCreateItem(ItemResource itemResource, int num) {
        if (num <= 0) {
            return null;
        }
        AbstractItem item = itemResource.getItemType().create();
        long identifyId = createIdentifyId();
        item.setObjectId(identifyId);
        item.init(itemResource);
        item.setNum(num);
        return item;
    }

    /**
     * 创建道具
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
        int size = num / overLimit + (num % overLimit == 0 ? 0 : 1);
        List<AbstractItem> result = new ArrayList<>();
        for (int i = 0; i < size - 1; i++) {
            result.add(doCreateItem(itemResource, overLimit));
            num -= overLimit;
        }
        result.add(doCreateItem(itemResource, num));
        return result;
    }

    /**
     * 创建一批道具
     *
     * @param
     * @param itemsMap
     * @return
     */
    public List<AbstractItem> createItems(Map<Integer, Integer> itemsMap) {
        List<AbstractItem> items = new ArrayList<>();
        for (Map.Entry<Integer, Integer> item : itemsMap.entrySet()) {
            int itemId = item.getKey();
            int num = item.getValue();
            items.addAll(createItems(itemId, num));
        }

        return items;
    }

    /**
     * 打印背包物品信息
     */
    public void printItems(String accountId) {
        ItemStorage itemStorage = getItemStorage(accountId);
        AbstractItem[] items = itemStorage.getItems();
        Account account = SpringContext.getAccountService().getAccount(accountId);
        if (items == null || itemStorage.getNum() == 0) {
            I18Utils.notifyMessage(account, I18nId.BAG_NULL_ITEMS);
            return;
        }
        SM_BagInfo packet = SM_BagInfo.valueOf(items);
        PacketSendUtil.send(account, packet);
    }

    /**
     * 打印仓库物品信息
     */
    public void printWareItems(String accountId) {
        Warehouse warehouse = getWarehouse(accountId);
        AbstractItem[] items = warehouse.getItems();
        Account account = SpringContext.getAccountService().getAccount(accountId);
        if (items == null || warehouse.getNum() == 0) {
            I18Utils.notifyMessage(account, I18nId.WAREHOUSE_NULL_ITEMS);
            return;
        }
        SM_WareInfo packet = SM_WareInfo.valueOf(items);
        PacketSendUtil.send(account, packet);
    }

    public ItemStorage getItemStorage(String accountId) {
        if (accountId == null) {
            return null;
        }
        ItemStorageEnt ent = getOrCreateItemStorageEnt(accountId);
        if (ent != null) {
            ent.doDeserialize();
            return ent.getItemStorage();
        }
        return null;
    }

    /**
     * 从背包根据唯一id获取道具
     *
     * @param accountId
     * @param id
     * @return
     */
    public AbstractItem getItemByObjectId(String accountId, long id) {
        return getItemStorage(accountId).getItemByObjectId(id);
    }

    private ItemStorageEnt getOrCreateItemStorageEnt(String accountId) {
        if (accountId == null) {
            return null;
        }
        ItemStorageEnt itemStorageEnt = itemStorageManager.getEnt(ItemStorageEnt.class, accountId);
        if (itemStorageEnt == null) {
            itemStorageEnt = ItemStorageEnt.valueOf(accountId);
            itemStorageManager.createEnt(itemStorageEnt);
            return itemStorageEnt;
        } else {
            return itemStorageEnt;
        }

    }


    public Warehouse getWarehouse(String accountId) {
        if (accountId == null) {
            return null;
        }
        WarehouseEnt ent = getOrCreateWarehouseEnt(accountId);
        if (ent != null) {
            ent.doDeserialize();
            return ent.getWarehouse();
        }
        return null;
    }

    private WarehouseEnt getOrCreateWarehouseEnt(String accountId) {
        if (accountId == null) {
            return null;
        }
        WarehouseEnt warehouseEnt = warehouseManager.getEnt(WarehouseEnt.class, accountId);
        if (warehouseEnt == null) {
            warehouseEnt = WarehouseEnt.valueOf(accountId);
            warehouseManager.createEnt(warehouseEnt);
            return warehouseEnt;
        } else {
            return warehouseEnt;
        }

    }

    private void saveItemStorageEnt(String accountId) {
        ItemStorageEnt ent = getOrCreateItemStorageEnt(accountId);
        ent.doSerialize();
        itemStorageManager.update();
    }

    private void saveItemStorageEnt(String accountId, ItemStorage itemStorage) {
        ItemStorageEnt ent = getOrCreateItemStorageEnt(accountId);
        ent.doSerialize(itemStorage);
        itemStorageManager.update();
    }

    private void saveWarehouseEnt(String accountId, Warehouse warehouse) {
        WarehouseEnt ent = getOrCreateWarehouseEnt(accountId);
        ent.doSerialize(warehouse);
        warehouseManager.update();
    }

    /**
     * 添加一批道具至背包
     *
     * @param account
     * @param abstractItems
     */
    public void addItemsToBag(Account account, List<AbstractItem> abstractItems) {
        if (abstractItems == null) {
            return;
        }
        ItemStorage storage = getItemStorage(account.getAccountId());
        if (!isEnoughPackSize(account, abstractItems)) {
            return;
        }
        for (AbstractItem item : abstractItems) {
            storage.addItem(item);
        }

        saveItemStorageEnt(account.getAccountId(), storage);
    }

    /**
     * 添加一个道具至背包
     *
     * @param account
     * @param abstractItem
     */
    public void addItemToBag(Account account, AbstractItem abstractItem) {
        if (abstractItem == null || !isEnoughPackSize(account)) {
            return;
        }

        ItemStorage storage = getItemStorage(account.getAccountId());
        storage.addItem(abstractItem);
        saveItemStorageEnt(account.getAccountId(), storage);
    }


    public void clearBag(Account account) {
        ItemStorage itemStorage = getItemStorage(account.getAccountId());
        itemStorage.clear();
        saveItemStorageEnt(account.getAccountId());
    }

    public void clearWarehouse(Account account) {
        Warehouse warehouse = getWarehouse(account.getAccountId());
        warehouse.clear();
        saveWarehouseEnt(account.getAccountId(), warehouse);
    }

    public void printStorage(Account account) {
        ItemStorage itemStorage = getItemStorage(account.getAccountId());
        System.out.println("【" + account.getName() + "】背包信息如下");
        itemStorage.printStorage();
    }


    /**
     * 移除背包道具
     *
     * @param account
     * @param modelId
     * @param num
     */
    public void reduceBagItemThrow(Account account, int modelId, int num) {

        ItemStorage itemStorage = getItemStorage(account.getAccountId());

        if (itemStorage.getItemNum(modelId) < num) {
            I18Utils.notifyMessageThrow(account, I18nId.ITEM_NOT_ENOUGH);
        }
        itemStorage.removeItemByModelId(modelId, num);
        saveItemStorageEnt(account.getAccountId(), itemStorage);
    }

    /**
     * 移除背包道具
     *
     * @param account
     * @param itemsMap
     */
    public void reduceBagItemThrow(Account account, Map<Integer, Integer> itemsMap) {

        for (Map.Entry<Integer, Integer> item : itemsMap.entrySet()) {
            int itemId = item.getKey();
            int num = item.getValue();
            reduceBagItemThrow(account, itemId, num);
        }

    }

    /**
     * 移除背包道具
     *
     * @param account
     * @param modelId
     * @param num
     */
    public void reduceBagItem(Account account, int modelId, int num) {

        ItemStorage itemStorage = getItemStorage(account.getAccountId());
        if (!itemStorage.isExistItem(modelId)) {
            I18Utils.notifyMessage(account, I18nId.BAG_NULL_ITEMS);
            return;
        }
        if (itemStorage.getItemNum(modelId) < num) {
            I18Utils.notifyMessage(account, I18nId.ITEM_NOT_ENOUGH);
            return;
        }
        itemStorage.removeItemByModelId(modelId, num);
        saveItemStorageEnt(account.getAccountId(), itemStorage);
    }
    /**
     * 移除背包道具
     *
     * @param account
     * @param item
     * @param num
     */
    public void reduceBagItem(Account account, AbstractItem item, int num) {
        int itemNum = item.getNum();
        if (itemNum < num) {
            return;
        }
        ItemStorage itemStorage = getItemStorage(account.getAccountId());
        if (!itemStorage.isExistItem(item.getItemModelId())) {
            PacketSendUtil.send(account, I18nId.BAG_NULL_ITEMS);
            return;
        }
        itemStorage.reduceItem(item.getObjectId(), num);
        saveItemStorageEnt(account.getAccountId(), itemStorage);
    }


    /**
     * 移除仓库道具
     *
     * @param account
     * @param item
     * @param num
     */
    public void reduceWarehouseItem(Account account, AbstractItem item, int num) {
        int itemNum = item.getNum();
        if (itemNum < num) {
            return;
        }
        Warehouse warehouse = getWarehouse(account.getAccountId());
        warehouse.reduceItem(item.getObjectId(), num);

        saveWarehouseEnt(account.getAccountId(), warehouse);
    }

    public void addItemToWarehouse(Account account, AbstractItem item) {
        if (item == null) {
            return;
        }
        Warehouse warehouse = getWarehouse(account.getAccountId());
        if (!isEnoughWarehouseSize(account, item.getNum())) {
            return;
        }
        warehouse.addItem(item);
        saveWarehouseEnt(account.getAccountId(), warehouse);
    }

    /**
     * 移动道具 从背包到仓库
     *
     * @param account
     * @param id      道具唯一id
     */
    public void moveBagToWarehouse(Account account, long id) {
        if (id <= 0) {
            return;
        }
        ItemStorage storage = getItemStorage(account.getAccountId());
        AbstractItem item = storage.getItemByObjectId(id);
        if (item == null || item.getNum() <= 0) {
            I18Utils.notifyMessage(account, I18nId.BAG_NO_ITEM);
            return;
        }
        ItemResource itemResource = getItemResource(item.getItemModelId());
        if (itemResource.getStorage() < 1 || !isEnoughWarehouseSize(account, 1)) {
            return;
        }
        //移除背包道具
        reduceBagItem(account, item, item.getNum());
        //添加道具至仓库
        addItemToWarehouse(account, item);

    }


    /**
     * 移动道具 从仓库到背包
     *
     * @param account
     * @param id      道具唯一id
     */
    public void moveWarehouseToBag(Account account, long id) {
        if (id <= 0) {
            return;
        }
        Warehouse warehouse = getWarehouse(account.getAccountId());
        AbstractItem item = warehouse.getItemByObjectId(id);
        if (item == null || item.getNum() <= 0 || !isEnoughPackSize(account)) {
            I18Utils.notifyMessage(account, I18nId.WAREHOUSE_NO_ITEM);
            return;
        }

        reduceWarehouseItem(account, item, item.getNum());
        ItemStorage itemStorage = getItemStorage(account.getAccountId());
        itemStorage.addItem(item);
        saveItemStorageEnt(account.getAccountId(), itemStorage);

    }

    /**
     * 是否道具足够
     * @param account
     * @param itemsMap
     */
    public void isEnoughItemThrow(Account account, Map<Integer, Integer> itemsMap) {
        ItemStorage itemStorage = getItemStorage(account.getAccountId());

        for (Map.Entry<Integer, Integer> item : itemsMap.entrySet()) {
            int itemId = item.getKey();
            int num = item.getValue();
            if (itemStorage.getItemNum(itemId) < num) {
                I18Utils.notifyMessageThrow(account, I18nId.ITEM_NOT_ENOUGH);
            }
        }

    }


}
