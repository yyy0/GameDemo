package com.server.common.executor.scene;

import com.SpringContext;
import com.server.common.command.AbstractCommand;
import com.server.common.command.ICommand;
import com.server.common.executor.NameThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yuxianming
 * @date 2019/6/13 10:36
 */
@Component
public class SceneExecutor {

    /**
     * 默认线程数量
     */
    private static final int DEFAULT_THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池数组
     */
    private static final ThreadPoolExecutor[] SCENE_EXECUTOR = new ThreadPoolExecutor[DEFAULT_THREAD_SIZE];

    public void start() {

        NameThreadFactory nameThreadFactory = new NameThreadFactory("场景线程");
        for (int i = 0; i < DEFAULT_THREAD_SIZE; i++) {
            SCENE_EXECUTOR[i] = new ThreadPoolExecutor(1, 1, 0L,
                    TimeUnit.MILLISECONDS,
                    //线程阻塞队列（链表，默认无边界）
                    new LinkedBlockingQueue<Runnable>(),
                    //线程工厂，用于创建线程名字
                    nameThreadFactory,
                    //ThreadPoolExecutor.DiscardPolicy 中，不能执行的任务将被删除。
                    new ThreadPoolExecutor.DiscardPolicy());
            SCENE_EXECUTOR[i].prestartAllCoreThreads();
        }
    }


    /**
     * 获取账号id与线程数的余数 将账号分配至指定的线程
     *
     * @param accountId
     * @return
     */
    private int modIndex(String accountId) {
        return Math.abs(accountId.hashCode() % DEFAULT_THREAD_SIZE);
    }

    public void addTask(ICommand command) {
        int modIndex = command.modIndex(DEFAULT_THREAD_SIZE);
        SCENE_EXECUTOR[modIndex].submit(() -> {
            if (!command.isCanceled()) {
                command.active();
            }
        });
    }


    /**
     * 延时命令
     *
     * @param command
     * @param delay
     */
    public final void schedule(AbstractCommand command, long delay) {

        command.setFuture(SpringContext.getScheduleService().schedule(() -> addTask(command), delay));

    }

    /**
     * 周期命令
     *
     * @param command
     * @param delay
     * @param period
     */
    public final void schedule(AbstractCommand command, long delay, long period) {

        command.setFuture(SpringContext.getScheduleService().scheduleAtFixedRate(() -> addTask(command), delay, period));
    }


}
