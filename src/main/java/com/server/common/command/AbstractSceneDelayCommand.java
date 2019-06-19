package com.server.common.command;

/**
 * 延时command
 *
 * @author yuxianming
 * @date 2019/6/15 19:48
 */
public abstract class AbstractSceneDelayCommand extends AbstractSceneCommand {

    private long delay;

    public AbstractSceneDelayCommand(String accountId, int mapId, long delay) {

        super(accountId, mapId);
        this.delay = delay;

    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
