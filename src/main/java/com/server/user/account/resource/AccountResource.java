package com.server.user.account.resource;

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
 * @date 2019/6/5 15:16
 */
@Resource
public class AccountResource {

    @ResId
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int id;

    /**
     * 升级所需经验
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int exp;

    /**
     * 对应等级加成的属性
     */
    @CsvCustomBindByName(converter = StringToAttrMapConverter.class)
    private Map<AttributeType, Attribute> attributes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Map<AttributeType, Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<AttributeType, Attribute> attributes) {
        this.attributes = attributes;
    }
}
