package com.server.map.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:32
 */
public class CM_MapInfo implements Serializable {

    private int mapId;

    public static CM_MapInfo valueOf(int mapId) {
        CM_MapInfo packet = new CM_MapInfo();
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
