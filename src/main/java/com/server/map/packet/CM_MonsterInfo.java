package com.server.map.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/11 18:31
 */
public class CM_MonsterInfo implements Serializable {

    private int mapId;

    public static CM_MonsterInfo valueOf(int mapId) {
        CM_MonsterInfo packet = new CM_MonsterInfo();
        packet.mapId = mapId;
        return packet;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
