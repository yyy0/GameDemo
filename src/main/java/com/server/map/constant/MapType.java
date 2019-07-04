package com.server.map.constant;

/**
 * @author yuxianming
 * @date 2019/7/1 21:11
 */
public enum MapType {

    /**
     * 普通地图
     */
    COMMON_MAP(0),

    /**
     * 挑战副本
     */
    CHALLENGE_DUNGEON(1),

    /**
     * 世界boss
     */
    WORLD_BOSS(2);


    /**
     * 地图类型id
     */
    private int id;

    MapType(int id) {
        this.id = id;
    }

    public static MapType typeOf(int id) {
        for (MapType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
