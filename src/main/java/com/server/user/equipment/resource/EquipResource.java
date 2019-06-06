package com.server.user.equipment.resource;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.server.common.resource.converter.StringToAttrMapConverter;
import com.server.common.resource.converter.StringToIntegerConverter;
import com.server.common.resource.reader.ResId;
import com.server.common.resource.reader.Resource;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.Attribute;

import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/3 21:32
 */
@Resource
public class EquipResource {

    @ResId
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int id;

    @CsvBindByName
    private String name;

    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int level;

    @CsvCustomBindByName(converter = StringToAttrMapConverter.class)
    private Map<AttributeType, Attribute> attributes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map<AttributeType, Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<AttributeType, Attribute> attributes) {
        this.attributes = attributes;
    }
}
