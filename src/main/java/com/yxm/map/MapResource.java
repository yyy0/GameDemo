package com.yxm.map;

/**
 * @author yuxianming
 * @date 2019/4/28 20:30
 */
public class MapResource {

    /**
     * 坐标标记 ☺：玩家， □：可行走点  ■：阻挡点
     */
    public static final char USER = '☺';
    public static final char ROAD = '□';
    public static final char WALL = '■';

    /**
     * 地图id
     */
    private int id;

    /**
     * 地图名称
     */
    private String name;

    /**
     * 地图资源 坐标信息
     */
    private int[][] mapres;

    /**
     * 地图默认初始位置
     */
    private int bornX;
    private int bornY;

    /**
     * 地图的宽、高
     */
    private int width;
    private int height;

    public static MapResource valueOf(int id, String name, int bornX, int bornY) {
        MapResource map = new MapResource();
        map.setId(id);
        map.setName(name);
        map.setBornX(bornX);
        map.setBornY(bornY);
        map.setMapres(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},}
        );

        return map;
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

    public int[][] getMapres() {
        return mapres;
    }

    public void setMapres(int[][] mapres) {
        this.mapres = mapres;
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
        return mapres[0].length;
    }

    public int getHeight() {
        return mapres.length;
    }
}
