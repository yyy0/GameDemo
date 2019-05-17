package com.server.map.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/17 15:46
 */
public class SM_AccountMove implements Serializable {
    private String accountId;

    private int newX;

    private int newY;

    private int preX;

    private int preY;

    public static SM_AccountMove valueOf(String accountId, int newX, int newY, int preX, int preY) {
        SM_AccountMove packet = new SM_AccountMove();
        packet.accountId = accountId;
        packet.newX = newX;
        packet.newY = newY;
        packet.preX = preX;
        packet.preY = preY;
        return packet;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public int getPreX() {
        return preX;
    }

    public void setPreX(int preX) {
        this.preX = preX;
    }

    public int getPreY() {
        return preY;
    }

    public void setPreY(int preY) {
        this.preY = preY;
    }

    @Override
    public String toString() {
        return "账号【" + accountId + "】旧坐标: " + preX + "," + preY + "  移动至新坐标: " + newX + "," + newY;
    }
}
