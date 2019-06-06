package com.server.map.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/6 11:28
 */
public class CM_Move implements Serializable {

    private int gridX;
    private int girdY;

    public static CM_Move valueOf(int girdX, int gridY) {
        CM_Move packet = new CM_Move();
        packet.gridX = girdX;
        packet.girdY = gridY;
        return packet;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGirdY() {
        return girdY;
    }

    public void setGirdY(int girdY) {
        this.girdY = girdY;
    }
}
