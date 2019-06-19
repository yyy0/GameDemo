package com.server.user.skill.resource;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.server.common.resource.converter.StringToIntegerConverter;
import com.server.common.resource.converter.StringToIntegerMap;
import com.server.common.resource.reader.ResId;
import com.server.common.resource.reader.Resource;

import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/11 23:28
 */
@Resource
public class SkillResource {

    @ResId
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int id;

    /**
     * 技能名称
     */
    @CsvBindByName
    private String name;

    /**
     * 技能描述
     */
    @CsvBindByName
    private String des;

    /**
     * 技能类型
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int type;

    /**
     * 目标数量
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int targetNum;

    /**
     * 攻击范围
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int range;

    /**
     * 伤害加成
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int damageRate;

    /**
     * 扣除蓝量
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int needMp;

    /**
     * 冷却cd
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int cd;

    /**
     * 学习所需道具
     */
    @CsvCustomBindByName(converter = StringToIntegerMap.class)
    private Map<Integer, Integer> item;

    /**
     * 施放buff
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int buff;

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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTargetNum() {
        return targetNum;
    }

    public void setTargetNum(int targetNum) {
        this.targetNum = targetNum;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getDamageRate() {
        return damageRate;
    }

    public void setDamageRate(int damageRate) {
        this.damageRate = damageRate;
    }

    public int getNeedMp() {
        return needMp;
    }

    public void setNeedMp(int needMp) {
        this.needMp = needMp;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public Map<Integer, Integer> getItem() {
        return item;
    }

    public void setItem(Map<Integer, Integer> item) {
        this.item = item;
    }

    public int getBuff() {
        return buff;
    }

    public void setBuff(int buff) {
        this.buff = buff;
    }
}
