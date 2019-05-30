package com.server.monster.resource;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.server.common.resource.converter.StringToIntegerConverter;
import com.server.common.resource.reader.ResId;
import com.server.common.resource.reader.Resource;

/**
 * @author yuxianming
 * @date 2019/5/28 12:28
 */
@Resource
public class MonsterResource {

    /**
     * 怪物id
     */
    @ResId
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int id;

    /**
     * 怪物名称
     */
    @CsvBindByName
    private String name;

    /**
     * 怪物等级
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int level;

    /**
     * 怪物血量
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int hp;

    /**
     * 怪物防御力
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int def;

    /**
     * 怪物攻击力
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int atk;

    @Override
    public String toString() {
        return "怪物信息{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", hp=" + hp +
                ", def=" + def +
                ", atk=" + atk +
                '}';
    }

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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }
}
