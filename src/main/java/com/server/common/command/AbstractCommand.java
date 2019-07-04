package com.server.common.command;

import java.util.concurrent.ScheduledFuture;

/**
 * @author yuxianming
 * @date 2019/6/13 18:29
 */
public abstract class AbstractCommand implements ICommand {

    private ScheduledFuture future;

    private boolean isCanceled = false;

    @Override
    public void active() {

    }

    @Override
    public void cancel() {
        if (future != null) {
            future.cancel(true);
        }
        isCanceled = true;
    }

    @Override
    public int modIndex(int size) {
        return 0;
    }

    public void setFuture(ScheduledFuture future) {
        this.future = future;
    }

    public ScheduledFuture getFuture() {
        return future;
    }

    @Override
    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }
}
