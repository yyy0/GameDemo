package com.server.user.account.packet;

import com.server.user.attribute.AttributeUtil;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.Attribute;
import com.server.user.fight.FightAccount;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/17 18:00
 */
public class SM_FightAccountInfo implements Serializable {

    /**
     * 当前血量
     */
    private long curHP;

    /**
     * 当前蓝量
     */
    private long curMP;

    private String accountId;

    private int gridX;

    private int girdY;

    private Map<String, String> attributes;

    public static SM_FightAccountInfo valueOf(FightAccount fightAccount) {
        SM_FightAccountInfo packet = new SM_FightAccountInfo();

        Map<AttributeType, Attribute> accountAtt = fightAccount.getAttributeMap();
        packet.accountId = fightAccount.getAccountId();
        packet.curHP = fightAccount.getHp();
        packet.curMP = fightAccount.getMp();
        packet.gridX = fightAccount.getGrid().getX();
        packet.girdY = fightAccount.getGrid().getY();
        packet.attributes = AttributeUtil.copyToClient(accountAtt);
        return packet;
    }

    public long getCurHP() {
        return curHP;
    }

    public void setCurHP(long curHP) {
        this.curHP = curHP;
    }

    public long getCurMP() {
        return curMP;
    }

    public void setCurMP(long curMP) {
        this.curMP = curMP;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "战斗账号信息如下：{" +
                "accountId='" + accountId + '\'' +
                ", curHP='" + curHP + '\'' +
                ", curMP=" + curMP +
                ", gridX=" + gridX +
                ", girdY=" + girdY +
                '}';
    }
}
