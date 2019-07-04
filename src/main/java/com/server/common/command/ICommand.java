package com.server.common.command;

/**
 * @author yuxianming
 * @date 2019/6/13 18:15
 */
public interface ICommand {

    /**
     * 执行任务
     */
    void active();

    boolean isCanceled();

    void cancel();

    /**
     * 获取执行线程的index
     *
     * @param size
     * @return
     */
    int modIndex(int size);
}
