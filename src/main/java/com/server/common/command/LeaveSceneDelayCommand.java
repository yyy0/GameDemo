package com.server.common.command;

import com.SpringContext;
import com.server.user.account.model.Account;

/**
 * 延时离开地图任务
 *
 * @author yuxianming
 * @date 2019/7/2 17:30
 */
public class LeaveSceneDelayCommand extends AbstractSceneDelayCommand {

    private Account account;
    /**
     * 离开后的目标地图id
     */
    private int targetMapId;


    public LeaveSceneDelayCommand(String accountId, int mapId, long delay, int targetMapId, Account account) {
        super(accountId, mapId, delay);
        this.account = account;
        this.targetMapId = targetMapId;
    }


    @Override
    public void action() {
        SpringContext.getWorldService().changeMap(account, targetMapId);
    }
}
