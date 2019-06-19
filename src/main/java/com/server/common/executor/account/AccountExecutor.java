package com.server.common.executor.account;

import com.server.common.executor.NameThreadFactory;
import com.server.session.model.TSession;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yuxianming
 * @date 2019/6/10 11:41
 */
@Component
public class AccountExecutor {

    /**
     * 默认线程数量
     */
    private static final int DEFAULT_THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池数组
     */
    private static final ThreadPoolExecutor[] ACCOUNT_EXECUTOR = new ThreadPoolExecutor[DEFAULT_THREAD_SIZE];

    public void start() {

        NameThreadFactory nameThreadFactory = new NameThreadFactory("账号线程");
        for (int i = 0; i < DEFAULT_THREAD_SIZE; i++) {
            ACCOUNT_EXECUTOR[i] = new ThreadPoolExecutor(1, 1, 0L,
                    TimeUnit.MILLISECONDS,
                    //线程阻塞队列（链表，默认无边界）
                    new LinkedBlockingQueue<Runnable>(),
                    //线程工厂，用于创建线程名字
                    nameThreadFactory,
                    //ThreadPoolExecutor.DiscardPolicy 中，不能执行的任务将被删除。
                    new ThreadPoolExecutor.DiscardPolicy());
            ACCOUNT_EXECUTOR[i].prestartAllCoreThreads();
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

    public void addTask(String accountId, Runnable task) {
        ACCOUNT_EXECUTOR[modIndex(accountId)].execute(task);
    }

    public void addTask(int modIndex, Runnable task) {
        ACCOUNT_EXECUTOR[modIndex].execute(task);
    }

    public void addSessionTask(TSession session, Runnable task) {
        ACCOUNT_EXECUTOR[session.getId() % DEFAULT_THREAD_SIZE].execute(task);
    }
}
