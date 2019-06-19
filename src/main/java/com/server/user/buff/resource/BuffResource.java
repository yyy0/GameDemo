package com.server.user.buff.resource;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.server.common.resource.converter.StringToBuffTypeConverter;
import com.server.common.resource.converter.StringToIntegerConverter;
import com.server.common.resource.reader.ResId;
import com.server.common.resource.reader.Resource;
import com.server.user.buff.constant.BuffType;

/**
 * @author yuxianming
 * @date 2019/6/13 9:35
 */
@Resource
public class BuffResource {

    /**
     * 主键
     */
    @ResId
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int id;

    /**
     * buff名称
     */
    @CsvBindByName
    private String name;

    /**
     * buff类型
     */
    @CsvCustomBindByName(converter = StringToBuffTypeConverter.class)
    private BuffType type;

    /**
     * 持续时间
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int duration;

    /**
     * 周期时间
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int periodTime;

    /**
     * 效果值
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int effectValue;

    /**
     * 效果百分比
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int effectRatio;

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

    public BuffType getType() {
        return type;
    }

    public void setType(BuffType type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(int periodTime) {
        this.periodTime = periodTime;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public void setEffectValue(int effectValue) {
        this.effectValue = effectValue;
    }

    public int getEffectRatio() {
        return effectRatio;
    }

    public void setEffectRatio(int effectRatio) {
        this.effectRatio = effectRatio;
    }
}
