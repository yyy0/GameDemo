package com;

import com.yxm.server.GameServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yuxianming
 * @date 2019/5/9 15:10
 */
public class Start {
    private static final String APPLICATION_CONTEXT = "applicationContext.xml";
    private static ClassPathXmlApplicationContext applicationContext;
    Logger logger = LoggerFactory.getLogger("START  ");

    public static void main(String[] args) {
        applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT);
        applicationContext.start();
        SpringContext.getGlobalService().onStart();
        new GameServer().connect();
    }
}
