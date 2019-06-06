package com.server.user.equipUpgrade.resource;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.server.common.resource.converter.StringToAttrMapConverter;
import com.server.common.resource.converter.StringToIntegerConverter;
import com.server.common.resource.converter.StringToIntegerMap;
import com.server.common.resource.reader.ResId;
import com.server.common.resource.reader.Resource;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.Attribute;

import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/6 10:34
 */
@Resource
public class EquipUpgradeResource {

    @ResId
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int id;

    @CsvBindByName
    private String name;

    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int position;

    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int level;

    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int nextLevel;

    @CsvCustomBindByName(converter = StringToAttrMapConverter.class)
    private Map<AttributeType, Attribute> attribute;

    @CsvCustomBindByName(converter = StringToIntegerMap.class)
    private Map<Integer, Integer> item;

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(int nextLevel) {
        this.nextLevel = nextLevel;
    }

    public Map<AttributeType, Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(Map<AttributeType, Attribute> attribute) {
        this.attribute = attribute;
    }

    public Map<Integer, Integer> getItem() {
        return item;
    }

    public void setItem(Map<Integer, Integer> item) {
        this.item = item;
    }
}
