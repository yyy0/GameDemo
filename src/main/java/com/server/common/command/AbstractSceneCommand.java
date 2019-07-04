package com.server.common.command;

import com.SpringContext;
import com.server.map.model.MapInfo;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/6/14 12:24
 */
public abstract class AbstractSceneCommand extends AbstractCommand {

    private String accountId;

    private int mapId;

    private MapInfo mapInfo;

    public AbstractSceneCommand(String accountId, MapInfo mapInfo) {
        this.accountId = accountId;
        this.mapInfo = mapInfo;
        this.mapId = mapInfo.getMapId();
    }

    public AbstractSceneCommand(String accountId, int mapId) {
        this.accountId = accountId;
        this.mapId = mapId;
        Account account = SpringContext.getAccountService().getAccount(accountId);
        this.mapInfo = SpringContext.getWorldService().getMapInfo(account, mapId);
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

    @Override
    public void active() {
        try {
            action();
        } catch (Exception e) {
            if (this.getAccountId() != null) {
                I18Utils.notifyMessage(this.getAccountId(), I18nId.OPERA_FAIL);
            }
        }
    }

    @Override
    public int modIndex(int size) {
        return Math.abs(mapId % size);
    }

    /**
     * 执行任务
     */
    public abstract void action();

    public MapInfo getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(MapInfo mapInfo) {
        this.mapInfo = mapInfo;
    }
}
