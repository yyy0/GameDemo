package com.server.user.fight.command;

import com.server.common.command.AbstractSceneRateCommand;

/**
 * @author yuxianming
 * @date 2019/6/17 21:00
 */
public class FightBuffCommand extends AbstractSceneRateCommand {


    private int buffId;

    public FightBuffCommand(String accountId, int mapId, long delay, long period) {
        super(accountId, mapId, delay, period);
    }

    @Override
    public void action() {

    }
}
