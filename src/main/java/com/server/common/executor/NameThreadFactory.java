package com.server.common.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuxianming
 * @date 2019/6/10 16:04
 * <p>
 * 线程工厂，用于创建线程名字
 */
public class NameThreadFactory implements ThreadFactory {

    private final String name;

    private final AtomicInteger index = new AtomicInteger();

    public NameThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, this.name + "-" + this.index.incrementAndGet());

    }
}
