package com.server.map.packet;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author yuxianming
 * @date 2019/5/17 15:44
 */
public class SM_MapInfo implements Serializable {

    private char[][] mapGrids;

    public static SM_MapInfo valueOf(char[][] mapGrids) {
        SM_MapInfo packet = new SM_MapInfo();
        packet.mapGrids = new char[mapGrids.length][mapGrids[0].length];
        for (int i = 0; i < mapGrids.length; i++) {
            packet.mapGrids[i] = Arrays.copyOf(mapGrids[i], mapGrids[i].length);
        }
        return packet;
    }


}
