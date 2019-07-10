package com.server.common.command;

import com.SpringContext;
import com.server.map.model.Scene;
import com.server.map.packet.SM_NotifyLeaveMap;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/7/3 14:42
 */
public class LeaveSceneNotifyCommand extends AbstractSceneDelayCommand {

    /**
     * 倒计时结束后抛出离开地图 command
     */
    private long countDown;

    private Account account;

    private int targetMapId;

    public LeaveSceneNotifyCommand(String accountId, int mapId, long delay, long countDown, Account account, int targetMapId) {
        super(accountId, mapId, delay);
        this.countDown = countDown;
        this.account = account;
        this.targetMapId = targetMapId;
    }

    @Override
    public void action() {
        // 通知倒计时离开地图
        SM_NotifyLeaveMap packet = SM_NotifyLeaveMap.valueOf(countDown, getMapId());
        PacketSendUtil.send(getAccountId(), packet);

        //抛出延时command 倒计时结束离开地图
        LeaveSceneDelayCommand delayCommand = new LeaveSceneDelayCommand(getAccountId(), getMapId(), countDown,
                targetMapId, account);
        Scene scene = SpringContext.getWorldService().getMapInfo(account, getMapId());
        scene.addCommand(delayCommand);
        SpringContext.getSceneExecutorService().submit(delayCommand);
    }
}
