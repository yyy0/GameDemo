package com.server.user.equipment.model;

import com.SpringContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.user.attribute.AttributeUtil;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.Attribute;
import com.server.user.equipUpgrade.resource.EquipUpgradeResource;
import com.server.user.equipment.constant.EquipmentPosition;
import com.server.user.equipment.resource.EquipResource;
import com.server.user.item.model.AbstractItem;
import com.server.user.item.resource.ItemResource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/13 20:34
 */
public class Equipment extends AbstractItem {


    /**
     * 装备阶数
     */
    private int grade;

    @Override
    public String toString() {

        return "{" +
                "装备类型=" + getEquipmentType() +
                ", 唯一id=" + objectId +
                ", 道具id=" + itemModelId +
                ", 名称=" + getName() +
                ", 阶数=" + grade +
                '}';
    }

    public int getEquipLevel() {
        return getEquipResource().getLevel();
    }

    public EquipResource getEquipResource() {
        return SpringContext.getEquipmentService().getEquipResource(itemModelId);
    }

    private int getEquipPositionInt() {
        for (EquipmentPosition position : EquipmentPosition.values()) {
            if (position.getEquipmentType() == getEquipmentType()) {
                return position.getId();
            }
        }
        return 0;
    }

    /**
     * 获取单件装备的最终属性集合
     *
     * @return
     */
    @JsonIgnore
    public Map<AttributeType, Attribute> getAttributeMap() {
        Map<AttributeType, Attribute> finalAttribute = deepCopy(getEquipResource().getAttributes());
        Map<AttributeType, Attribute> upGradeAttribute;
        if (getGradeAttribute() == null) {
            upGradeAttribute = null;
            AttributeUtil.computeAttribute(upGradeAttribute, finalAttribute);
            return finalAttribute;
        }
        upGradeAttribute = deepCopy(getGradeAttribute());
        AttributeUtil.computeAttribute(upGradeAttribute, finalAttribute);
        return finalAttribute;
    }

    public Map<AttributeType, Attribute> deepCopy(Map<AttributeType, Attribute> original) {

        Map<AttributeType, Attribute> copy = new HashMap<>();
        for (Map.Entry<AttributeType, Attribute> entry : original.entrySet()) {
            copy.put(entry.getKey(), Attribute.valueOf(entry.getKey(), entry.getValue().getValue()));
        }
        return copy;
    }


    public EquipUpgradeResource getEquipUpResource() {
        return SpringContext.getEquipUpgradeService().getEquipUpResource(getEquipPositionInt(), grade);
    }

    /**
     * 获取装备升阶属性
     */
    public Map<AttributeType, Attribute> getGradeAttribute() {
        return getEquipUpResource().getAttribute();
    }

    public void upGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public void init(ItemResource itemResource) {
        super.init(itemResource);
        EquipResource equipResource = SpringContext.getEquipmentService().getEquipResource(itemResource.getId());

    }
}
