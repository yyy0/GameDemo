package com.server.map.command;

import com.SpringContext;
import com.server.common.command.AbstractSceneCommand;
import com.server.map.model.Scene;
import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/6/21 17:56
 */
public class MapInfoCommand extends AbstractSceneCommand {

    private Account account;

    public MapInfoCommand(String accountId, Scene scene) {
        super(accountId, scene);
    }

    public MapInfoCommand(String accountId, int mapId) {
        super(accountId, mapId);
    }

    @Override
    public void action() {
        SpringContext.getWorldService().doPrintMapInfo(account, getMapId());
    }

    public static MapInfoCommand valueOf(Account account, int mapId) {
        MapInfoCommand command = new MapInfoCommand(account.getAccountId(), mapId);
        command.account = account;
        return command;
    }
}
