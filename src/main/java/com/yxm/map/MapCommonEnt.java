package com.yxm.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxm.tool.JsonUtils;
import com.yxm.user.account.Account;

import javax.persistence.*;
import java.util.List;

/**
 * @author yuxianming
 * @date 2019/4/28 20:56
 */
@Entity
@Table(name = "map_common")
public class MapCommonEnt {
    @Id
    @Column(name = "mapid", nullable = false)
    private int mapId;

    @Lob
    @Column(name = "accounts")
    private String accountJson;


    @Transient
    private List<Account> accounts;

    ObjectMapper mapper = new ObjectMapper();


    public void doSerialize() throws Exception {
        if (accounts != null) {
            accountJson = JsonUtils.obj2json(accounts);
        }

    }

    public void doDeSerialize() throws Exception {
        if (this.accountJson != null) {
            //json转list
            this.accounts = JsonUtils.json2list(accountJson, Account.class);
        }

    }

    public MapCommonEnt() throws JsonProcessingException {
    }

    public List<Account> getAccounts() throws Exception {
        if (accounts == null) {
            synchronized (this) {
                if (accounts == null) {
                    accounts = JsonUtils.json2list(accountJson, Account.class);
                }
            }
        }
        return accounts;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
