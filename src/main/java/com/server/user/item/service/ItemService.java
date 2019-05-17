package com.server.user.item.service;

import com.server.user.item.resource.ItemResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/5/13 10:06
 */
@Component
public class ItemService {


    @Autowired
    private ItemManager itemManager;

    public void initItemResource() {
        itemManager.initItems();
    }

    public ItemResource getItemResource(int itemModelId) {
        return itemManager.getItemResource(itemModelId);
    }


}
