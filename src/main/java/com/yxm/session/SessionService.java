package com.yxm.session;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuxianming
 * @date 2019/5/9 11:15
 */
@Component
public class SessionService {

    /**
     * 已登录的session
     */
    private ConcurrentHashMap<String, TSession> accountSession = new ConcurrentHashMap<>();

    public TSession getSession(String accountId) {
        return accountSession.get(accountId);
    }

    public void CreateSession(TSession session) {

    }
}
