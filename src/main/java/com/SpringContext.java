package com;

import com.yxm.command.service.Command;
import com.yxm.command.service.CommandFacade;
import com.yxm.common.GlobalService;
import com.yxm.login.service.LoginService;
import com.yxm.map.service.WorldService;
import com.yxm.user.account.service.AccountService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author yuxianming
 * @date 2019/4/25 15:18
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static SpringContext instance;
    @Autowired
    public ApplicationContext applicationContext;

    @Autowired
    public Command command;

    @Autowired
    public CommandFacade commandFacade;

    @Autowired
    public WorldService worldService;

    @Autowired
    public AccountService accountService;

    @Autowired
    public LoginService loginService;

    @Autowired
    public GlobalService globalService;


    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    public SpringContext() {
        System.out.println();
    }

    @PostConstruct
    private final void init() {
        instance = this;
    }


    public static CommandFacade getCommandFacade() {
        return instance.commandFacade;
    }

    public static SpringContext getInstance() {
        return instance;
    }

    public ApplicationContext getApplicationContext() {
        return instance.applicationContext;
    }

    public static Command getCommand() {
        return instance.command;
    }

    public static WorldService getWorldService() {
        return instance.worldService;
    }

    public static AccountService getAccountService() {
        return instance.accountService;
    }

    public static LoginService getLoginService() {
        return instance.loginService;
    }

    public static GlobalService getGlobalService() {
        return instance.globalService;
    }


}
