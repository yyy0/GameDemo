package com.yxm.user.account.model;

import java.util.Date;


/**
 * @author yuxianming
 * @date 2019/4/25 17:35
 */

public class Account {

    private String accountId;

    private String name;

    private String pwd;

    private Date createTime;

    /**
     * 当前地图id  也是上次所在地图
     */

    private int mapId;

    /**
     * 当前地图坐标（也是上次地图坐标）
     */

    private int gridX;

    private int girdY;


    /**
     * 初始化账号信息
     *
     * @param account
     * @param name
     * @param pwd
     * @return
     */
    public static Account valueOf(String account, String name, String pwd) {
        Account info = new Account();
        info.accountId = account;
        info.name = name;
        info.pwd = pwd;
        info.mapId = 1001;
        info.gridX = 1;
        info.girdY = 1;
        info.createTime = new Date();
        return info;
    }

    public static Account valueOf(String account) {
        Account info = new Account();
        return info;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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
        return "账号信息：Account{" +
                "accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", mapId=" + mapId +
                ", gridX=" + gridX +
                ", girdY=" + girdY +
                '}';
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
