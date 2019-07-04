package com.server.map.resource;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.server.common.resource.converter.*;
import com.server.common.resource.reader.ResId;
import com.server.common.resource.reader.Resource;
import com.server.map.constant.MapType;

import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/4/28 20:30
 */
@Resource
public class MapResource {

    /**
     * 地图id
     */
    @ResId
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int id;
    /**
     * 地图名称
     */
    @CsvBindByName
    private String name;

    /**
     * 地图资源 坐标信息
     */
    @CsvCustomBindByName(converter = StringToIntMatrixConverter.class)
    private int[][] mapRes;

    /**
     * 地图默认初始位置
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int bornX;
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int bornY;

    /**
     * 地图的宽、高
     */
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int width;
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int height;

    @CsvCustomBindByName(converter = StringToMapTypeConverter.class)
    private MapType type;

    @CsvCustomBindByName(converter = StringToIntegerMap.class)
    private Map<Integer, Integer> reward;

    @CsvCustomBindByName(converter = StringToLongConverter.class)
    private long duration;


//    public static MapResource valueOf(int id, String name, int bornX, int bornY) {
//        MapResource map = new MapResource();
//        map.setId(id);
//        map.setName(name);
//        map.setBornX(bornX);
//        map.setBornY(bornY);
//        map.setMapRes(new int[][]{
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},}
//        );
//
//        return map;
//    }

    public void checkGrid(int gridX, int gridY) {
        if (gridX < 0 || gridY < 0 || gridX > getHeight() - 1 || gridY > getWidth() - 1) {
            throw new IllegalArgumentException("非法坐标！！！");
        }
    }

    /**
     * 是否为可行走点
     *
     * @param gridX
     * @param gridY
     * @return
     */
    public boolean isWalkGrid(int gridX, int gridY) {
        return mapRes[gridX][gridY] != 0;
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

    public int[][] getMapRes() {
        return mapRes;
    }

    public void setMapRes(int[][] mapRes) {
        this.mapRes = mapRes;
    }

    public int getBornX() {
        return bornX;
    }

    public void setBornX(int bornX) {
        this.bornX = bornX;
    }

    public int getBornY() {
        return bornY;
    }

    public void setBornY(int bornY) {
        this.bornY = bornY;
    }

    public int getWidth() {
        return mapRes[0].length;
    }

    public int getHeight() {
        return mapRes.length;
    }

    public MapType getType() {
        return type;
    }

    public void setType(MapType type) {
        this.type = type;
    }

    public Map<Integer, Integer> getReward() {
        return reward;
    }

    public void setReward(Map<Integer, Integer> reward) {
        this.reward = reward;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
