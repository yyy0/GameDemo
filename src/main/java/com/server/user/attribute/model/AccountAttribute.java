package com.server.user.attribute.model;

import com.server.user.account.model.Account;
import com.server.user.attribute.AttributeUtil;
import com.server.user.attribute.constant.AttributeModel;
import com.server.user.attribute.constant.AttributeType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/4 16:51
 */
public class AccountAttribute {


    /**
     * 玩家最终总属性
     */
    private Map<AttributeType, Attribute> accountAttribute = new HashMap<>();

    /**
     * 模块属性
     */
    private Map<AttributeModel, Map<AttributeType, Attribute>> attributeModels = new HashMap<>();

    /**
     * 用于计算最终属性的map
     */
    private Map<AttributeType, Attribute> calculateAttribute = new HashMap<>();

    public AccountAttribute() {
    }

    public AccountAttribute(Account account) {
        for (AttributeModel model : AttributeModel.values()) {
            attributeModels.put(model, model.getAttributeModel(account));
        }
        compute(attributeModels, accountAttribute);
    }


    /**
     * 根据各个功能模块属性计算最终角色属性
     *
     * @param attributeModels
     * @param accountAttribute
     */
    public void compute(Map<AttributeModel, Map<AttributeType, Attribute>> attributeModels, Map<AttributeType, Attribute> accountAttribute) {
        //清空当前计算属性
        for (Attribute temp : calculateAttribute.values()) {
            temp.setValue(0);
        }

        //将模块属性的类型累计到计算属性的map里
        for (Map.Entry<AttributeModel, Map<AttributeType, Attribute>> entry : attributeModels.entrySet()) {
            AttributeUtil.computeAttribute(entry.getValue(), calculateAttribute);
        }

        //计算最终属性赋值到accountAttribute
        for (AttributeType type : calculateAttribute.keySet()) {
            long value = type.compute(calculateAttribute);
            accountAttribute.put(type, Attribute.valueOf(type, value));

        }
    }

    public void putAttributeModel(AttributeModel model, Map<AttributeType, Attribute> attributes) {
        if (attributes != null) {
            attributeModels.put(model, attributes);
        }
    }

    /**
     * 根据当前模块属性 重新计算
     */
    public void recompute() {
        compute(attributeModels, accountAttribute);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Attribute attribute : accountAttribute.values()) {
            if (attribute != null) {
                stringBuilder.append("属性：").
                        append(attribute.getType().getDesc()).
                        append(" 属性值:").append(attribute.getValue()).
                        append("\r\n");
            }
        }
        return String.valueOf(stringBuilder);
    }

    public Map<AttributeType, Attribute> getAccountAttribute() {
        return accountAttribute;
    }

    public void setAccountAttribute(Map<AttributeType, Attribute> accountAttribute) {
        this.accountAttribute = accountAttribute;
    }

    public Map<AttributeModel, Map<AttributeType, Attribute>> getAttributeModels() {
        return attributeModels;
    }

    public void setAttributeModels(Map<AttributeModel, Map<AttributeType, Attribute>> attributeModels) {
        this.attributeModels = attributeModels;
    }

    public Map<AttributeType, Attribute> getCalculateAttribute() {
        return calculateAttribute;
    }

    public void setCalculateAttribute(Map<AttributeType, Attribute> calculateAttribute) {
        this.calculateAttribute = calculateAttribute;
    }
}
