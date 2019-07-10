package com.server.common.command;

import com.SpringContext;
import com.server.map.model.Scene;
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

    private Scene scene;

    public AbstractSceneCommand(String accountId, Scene scene) {
        this.accountId = accountId;
        this.scene = scene;
        this.mapId = scene.getMapId();
    }

    public AbstractSceneCommand(String accountId, int mapId) {
        this.accountId = accountId;
        this.mapId = mapId;
        Account account = SpringContext.getAccountService().getAccount(accountId);
        this.scene = SpringContext.getWorldService().getMapInfo(account, mapId);
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

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
