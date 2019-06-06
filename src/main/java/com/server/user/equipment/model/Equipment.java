package com.server.user.equipment.model;

import com.SpringContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.Attribute;
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
     * 装备加成属性 读取配置
     */
    @JsonIgnore
    private transient Map<AttributeType, Attribute> attributeMap = new HashMap<>();

    @Override
    public String toString() {

        return "{" +
                "装备类型=" + getEquipmentType() +
                ", 唯一id=" + objectId +
                ", 道具id=" + itemModelId +
                ", 名称=" + getName() +
                '}';
    }

    public int getEquipLevel() {
        return getEquipResource().getLevel();
    }

    public EquipResource getEquipResource() {
        return SpringContext.getEquipmentService().getEquipResource(itemModelId);
    }

    public Map<AttributeType, Attribute> getAttributeMap() {
        return attributeMap.size() > 0 ? attributeMap : getEquipResource().getAttributes();

    }

    public void setAttributeMap(Map<AttributeType, Attribute> attributeMap) {
        this.attributeMap = attributeMap;
    }

    @Override
    public void init(ItemResource itemResource) {
        super.init(itemResource);
        EquipResource equipResource = SpringContext.getEquipmentService().getEquipResource(itemResource.getId());
        attributeMap = equipResource.getAttributes();

    }
}
