package com.server.user.account.packet;

import com.SpringContext;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.AccountAttribute;
import com.server.user.attribute.model.Attribute;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

    private Map<String, Long> attributes;

    public static SM_AccountInfo valueOf(String accountId, String name, int mapId, int girdX, int gridY) {
        SM_AccountInfo packet = new SM_AccountInfo();
        packet.accountId = accountId;
        packet.name = name;
        packet.mapId = mapId;
        packet.gridX = girdX;
        packet.girdY = gridY;
        packet.attributes = new HashMap<>();
        AccountAttribute accountAttr = SpringContext.getAttributeManager().getAccountAttribute(accountId);
        Map<AttributeType, Attribute> accountAtt = accountAttr.getAccountAttribute();
        for (Attribute attribute : accountAtt.values()) {
            packet.attributes.put(attribute.getType().getDesc(), attribute.getValue());
        }
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

    public Map<String, Long> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Long> attributes) {
        this.attributes = attributes;
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
