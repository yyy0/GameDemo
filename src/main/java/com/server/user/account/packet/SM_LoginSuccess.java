package com.server.user.account.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/17 15:48
 */
public class SM_LoginSuccess implements Serializable {

    /**
     * 账号id
     */
    private String accountId;
    /**
     * 地图id
     */
    private int mapId;

    public static SM_LoginSuccess valueOf(String accountId, int mapId) {
        SM_LoginSuccess packet = new SM_LoginSuccess();
        packet.accountId = accountId;
        packet.mapId = mapId;
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
}
