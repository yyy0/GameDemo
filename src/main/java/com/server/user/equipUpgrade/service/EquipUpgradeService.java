package com.server.user.equipUpgrade.service;

import com.SpringContext;
import com.server.user.account.model.Account;
import com.server.user.equipUpgrade.resource.EquipUpgradeResource;
import com.server.user.equipment.constant.EquipmentPosition;
import com.server.user.equipment.resource.EquipResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/6 10:18
 */
@Component
public class EquipUpgradeService {


    /**
     * <部位，<装备阶数，升阶配置>> Map
     */
    private Map<Integer, Map<Integer, EquipResource>> upGradeResourceMap;

    public void initEquipUpResource() {
        Map<Integer, Object> upgradeResources = SpringContext.getResourceManager().getResources(EquipUpgradeResource.class.getSimpleName());
        Map<Integer, Map<Integer, EquipResource>> positionResource = new HashMap<>(EquipmentPosition.values().length);

    }

    /**
     * 装备升阶
     *
     * @param account
     * @param position
     */
    public void equipUpgrade(Account account, int position) {

    }
}
