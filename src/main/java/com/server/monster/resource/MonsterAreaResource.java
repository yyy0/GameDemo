package com.server.monster.resource;

import com.opencsv.bean.CsvCustomBindByName;
import com.server.common.resource.converter.StringToIntegerConverter;
import com.server.common.resource.reader.ResId;
import com.server.common.resource.reader.Resource;

/**
 * @author yuxianming
 * @date 2019/6/11 11:04
 */
@Resource
public class MonsterAreaResource {

    /**
     * 主键 刷怪id
     */
    @ResId
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int id;

    /**
     * 地图id
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int mapId;

    /**
     * 怪物id
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int monsterId;

    /**
     * 怪物名称
     */
    private String name;

    /**
     * 刷怪x坐标
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int gridX;

    /**
     * 刷怪y坐标
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int gridY;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }
}
