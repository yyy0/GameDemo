package com.server.map.command;

import com.SpringContext;
import com.server.common.command.AbstractSceneCommand;
import com.server.map.model.Grid;
import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/6/21 16:55
 */
public class MapMoveCommand extends AbstractSceneCommand {

    private Grid grid;
    private Account account;

    public MapMoveCommand(String accountId, int mapId) {
        super(accountId, mapId);
    }

    @Override
    public void action() {
        SpringContext.getWorldService().doMove(account, grid);
    }

    public static MapMoveCommand valueOf(Account account, Grid grid) {
        MapMoveCommand command = new MapMoveCommand(account.getAccountId(), account.getMapId());
        command.grid = grid;
        command.account = account;
        return command;

    }
}
