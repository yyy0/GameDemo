package com.server.common.executor.schedule;

import com.server.common.executor.NameThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yuxianming
 * @date 2019/6/14 10:08
 */
@Component
public class ScheduleService {

    private ScheduledThreadPoolExecutor scheduledExecutor;

    private int poolSize = 1;

    public void init() {
        scheduledExecutor = new ScheduledThreadPoolExecutor(poolSize, new NameThreadFactory("定时器"), new ThreadPoolExecutor.DiscardPolicy());
        scheduledExecutor.prestartAllCoreThreads();
    }


    public final ScheduledFuture<?> schedule(Runnable task, long delay) {
        return scheduledExecutor.schedule(task, delay, TimeUnit.MILLISECONDS);
    }


    /**
     * 指定周期执行任务
     *
     * @param task
     * @param delay
     * @param period
     * @return
     */
    public final ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long delay, long period) {
        return scheduledExecutor.scheduleAtFixedRate(task, delay, period, TimeUnit.MILLISECONDS);
    }
}
