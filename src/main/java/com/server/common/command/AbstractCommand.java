package com.server.common.command;

import java.util.concurrent.ScheduledFuture;

/**
 * @author yuxianming
 * @date 2019/6/13 18:29
 */
public abstract class AbstractCommand implements ICommand {

    private ScheduledFuture future;

    @Override
    public void active() {

    }

    @Override
    public int modIndex(int size) {
        return 0;
    }

    public void setFuture(ScheduledFuture future) {
        this.future = future;
    }
}
