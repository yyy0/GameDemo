package com.server.common.executor.account;

import com.server.session.model.TSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/10 15:04
 */
@Component
public class AccountExecutorService {

    @Autowired
    public AccountExecutor accountExecutor;

    public void init() {
        accountExecutor.start();
    }


    public void addTask(String accountId, Runnable task) {
        accountExecutor.addTask(accountId, task);
    }

    public void addTask(int modIndex, Runnable task) {
        accountExecutor.addTask(modIndex, task);
    }

    public void addSessionTask(TSession session, Runnable task) {
        accountExecutor.addSessionTask(session, task);
    }


}
