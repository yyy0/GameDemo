package com;

import com.yxm.command.Command;
import com.yxm.command.CommandFacade;
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


}
