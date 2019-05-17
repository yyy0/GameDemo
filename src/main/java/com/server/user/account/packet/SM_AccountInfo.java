package com.server.user.account.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/17 15:46
 */
public class SM_AccountInfo implements Serializable {

    private String accountId;

    private String name;

    private int mapId;

    private int gridX;

    private int girdY;

    public static SM_AccountInfo valueOf(String accountId, String name, int mapId, int girdX, int gridY) {
        SM_AccountInfo packet = new SM_AccountInfo();
        packet.accountId = accountId;
        packet.name = name;
        packet.mapId = mapId;
        packet.gridX = girdX;
        packet.girdY = gridY;
        return packet;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
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

    @Override
    public String toString() {
        return "账号信息如下：{" +
                "accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", mapId=" + mapId +
                ", gridX=" + gridX +
                ", girdY=" + girdY +
                '}';
    }
}
