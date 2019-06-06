package com.server.user.account.model;

import com.SpringContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.user.account.resource.AccountResource;
import com.server.user.attribute.constant.AttributeModel;
import com.server.user.attribute.model.AccountAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * @author yuxianming
 * @date 2019/4/25 17:35
 */

public class Account {

    public static Logger logger = LoggerFactory.getLogger(Account.class);

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

    private int level;

    @JsonIgnore
    private transient AccountAttribute attribute;


    /**
     * 初始化账号信息
     *
     * @param accountId
     * @param name
     * @param pwd
     * @return
     */
    public static Account valueOf(String accountId, String name, String pwd) {
        Account info = new Account();
        info.accountId = accountId;
        info.name = name;
        info.pwd = pwd;
        info.mapId = 1;
        info.gridX = 1;
        info.girdY = 1;
        info.createTime = new Date();
        info.level = 1;
        AccountResource resource = SpringContext.getAccountService().getAccountResource(info.level);
        //人物基础属性
        SpringContext.getAttributeManager().putAttributes(accountId, AttributeModel.ACCOUNT_BASE, resource.getAttributes());
        return info;
    }

    public static Account valueOf(String account) {
        Account info = new Account();
        return info;
    }


    public AccountAttribute getAccountAttribute(String accountId) {
        return SpringContext.getAttributeManager().getAccountAttribute(accountId);
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public AccountAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(AccountAttribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {

        AccountAttribute accountAttribute = getAccountAttribute(accountId);
        return "账号信息：Account{" +
                "accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", level=" + level +
                ", mapId=" + mapId +
                ", gridX=" + gridX +
                ", girdY=" + girdY +
                '}' +
                "\r\n" + accountAttribute;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
