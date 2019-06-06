package com.server.user.attribute.constant;

import com.SpringContext;
import com.server.user.account.model.Account;
import com.server.user.account.resource.AccountResource;
import com.server.user.attribute.AttributeUtil;
import com.server.user.attribute.model.Attribute;
import com.server.user.equipment.constant.EquipmentPosition;
import com.server.user.equipment.model.EquipStorage;
import com.server.user.equipment.model.Equipment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/3 22:07
 * 功能属性模块
 */
public enum AttributeModel {
    //装备模块属性
    EQUIPMENT("装备") {
        @Override
        public Map<AttributeType, Attribute> getAttributeModel(Account account) {
            EquipStorage equipStorage = SpringContext.getEquipmentService().getEquipStorage(account.getAccountId());
            Map<AttributeType, Attribute> attributeResult = new HashMap<>();
            Map<EquipmentPosition, Equipment> equipments = equipStorage.getEquipments();
            for (Equipment equipment : equipments.values()) {
                if (equipment != null) {
                    AttributeUtil.computeAttribute(equipment.getAttributeMap(), attributeResult);
                }
            }
            return attributeResult;
        }
    },
    //人物基础模块属性
    ACCOUNT_BASE("人物基础") {
        @Override
        public Map<AttributeType, Attribute> getAttributeModel(Account account) {
            AccountResource resource = SpringContext.getAccountService().getAccountResource(account.getLevel());
            return resource.getAttributes();
        }
    };


    private String name;

    AttributeModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<AttributeType, Attribute> getAttributeModel(Account account) {
        return null;
    }

}
