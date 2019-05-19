package com.server.map.resource;

/**
 * @author yuxianming
 * @date 2019/4/28 20:30
 */
public class MapResource {


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

    public void checkGrid(int gridX, int gridY) {
        if (gridX < 0 || gridY < 0 || gridX > getHeight() - 1 || gridY > getWidth() - 1) {
            throw new IllegalArgumentException("非法坐标！！！");
        }
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
