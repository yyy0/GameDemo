package com.server.common.command;

/**
 * 场景周期command
 *
 * @author yuxianming
 * @date 2019/6/15 19:50
 */
public abstract class AbstractSceneRateCommand extends AbstractSceneDelayCommand {

    private long period;

    public AbstractSceneRateCommand(String accountId, int mapId, long delay, long period) {
        super(accountId, mapId, delay);
        this.period = period;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}
