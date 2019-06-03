package com.server.map.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/17 15:38
 */
public class SM_ChangeMap implements Serializable {

    private String accountId;

    /**
     * 目标地图id
     */
    private int mapId;

    /**
     * 旧地图id
     */
    private int preMapId;

    public static SM_ChangeMap valueOf(String accountId, int mapId, int preMapId) {
        SM_ChangeMap packet = new SM_ChangeMap();
        packet.accountId = accountId;
        packet.mapId = mapId;
        packet.preMapId = preMapId;
        return packet;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getPreMapId() {
        return preMapId;
    }

    public void setPreMapId(int preMapId) {
        this.preMapId = preMapId;
    }

    @Override
    public String toString() {
        return " 账号【" + accountId + "】从旧地图【" + preMapId + "】切换至新地图【" + mapId + "】";
    }
}
