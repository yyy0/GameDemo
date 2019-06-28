package com.server.map.command;

import com.SpringContext;
import com.server.common.command.AbstractSceneCommand;
import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/6/21 15:45
 */
public class EnterMapCommand extends AbstractSceneCommand {

    private Account account;

    private int targetMapId;

    public EnterMapCommand(String accountId, int mapId) {
        super(accountId, mapId);
    }

    @Override
    public void action() {
        SpringContext.getWorldService().enterMap(account, targetMapId);
    }

    public static EnterMapCommand valueOf(Account account, int targetMapId) {
        EnterMapCommand command = new EnterMapCommand(account.getAccountId(), account.getMapId());
        command.account = account;
        command.targetMapId = targetMapId;
        return command;
    }
}
