package com.server.map.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:24
 */
public class CM_ChangeMap implements Serializable {

    private int mapId;

    public static CM_ChangeMap valueOf(int mapId) {
        CM_ChangeMap packet = new CM_ChangeMap();
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
