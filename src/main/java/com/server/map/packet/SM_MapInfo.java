package com.server.map.packet;

import com.server.map.model.MapInfo;
import com.server.map.resource.MapResource;
import com.server.tool.TimeUtil;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author yuxianming
 * @date 2019/5/17 15:44
 */
public class SM_MapInfo implements Serializable {

    private char[][] mapGrids;


    private long coutDown;

    public static SM_MapInfo valueOf(MapInfo mapInfo) {
        SM_MapInfo packet = new SM_MapInfo();
        char[][] mapGrids = mapInfo.printInfo();
        packet.mapGrids = new char[mapGrids.length][mapGrids[0].length];

        for (int i = 0; i < mapGrids.length; i++) {
            packet.mapGrids[i] = Arrays.copyOf(mapGrids[i], mapGrids[i].length);
        }
        MapResource resource = mapInfo.getMapResource();
        long duration = resource.getDuration();
        if (duration == 0) {
            packet.coutDown = 0;
        } else {
            packet.coutDown = duration - (TimeUtil.now() - mapInfo.getCreateTime());
        }

        return packet;
    }

    public long getCoutDown() {
        return coutDown;
    }

    public void setCoutDown(long coutDown) {
        this.coutDown = coutDown;
    }

    public char[][] getMapGrids() {
        return mapGrids;
    }

    public void setMapGrids(char[][] mapGrids) {
        this.mapGrids = mapGrids;
    }
}
