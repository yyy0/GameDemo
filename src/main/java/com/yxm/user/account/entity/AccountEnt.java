package com.yxm.user.account.entity;

import com.yxm.tool.JsonUtils;
import com.yxm.user.account.model.Account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yuxianming
 * @date 2019/5/7 21:57
 */
@Entity
@Table(name = "account")
public class AccountEnt implements Serializable {
    @Id
    @Column(name = "accountId", nullable = false)
    private String accountId;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号名称'", nullable = false)
    private String name;

    @Column(name = "pwd", nullable = false)
    private String pwd;

    @Transient
    private Account account;

    /**
     * json数据
     */
    @Lob
    @Column(columnDefinition = "blob comment '账号基本数据'")
    private byte[] accountData;


    @Column(columnDefinition = "timestamp comment '账号创建时间'")
    private Date createTime;

    public static AccountEnt valueOf(String accountId) {
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.accountId = accountId;
        return accountEnt;
    }

    public boolean doSerialize() {
        this.accountData = JsonUtils.objToByte(account);
        return true;
    }

    public boolean doDeserialize() {
        this.account = JsonUtils.byteToObj(accountData, Account.class);
        return true;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public byte[] getAccountData() {
        return accountData;
    }

    public void setAccountData(byte[] accountData) {
        this.accountData = accountData;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
