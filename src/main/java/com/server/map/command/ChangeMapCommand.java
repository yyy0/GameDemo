package com.server.map.command;

import com.SpringContext;
import com.server.common.command.AbstractSceneCommand;
import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/6/15 20:53
 */
public class ChangeMapCommand extends AbstractSceneCommand {

    private Account account;

    private int targetMapId;

    public ChangeMapCommand(String accountId, int mapId) {
        super(accountId, mapId);
    }

    @Override
    public void action() {
        SpringContext.getWorldService().serverChangeMap(account, targetMapId);
    }

    public static ChangeMapCommand valueOf(Account account, int targetMapId) {
        ChangeMapCommand cmd = new ChangeMapCommand(account.getAccountId(), account.getMapId());
        cmd.account = account;
        cmd.targetMapId = targetMapId;
        return cmd;
    }
}
