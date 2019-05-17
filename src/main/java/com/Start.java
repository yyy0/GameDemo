package com;

import com.server.server.GameServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * 服务器正式启动类
 * @author yuxianming
 * @date 2019/5/9 15:10
 */
public class Start {
    private static final String APPLICATION_CONTEXT = "applicationContext.xml";
    private static ClassPathXmlApplicationContext applicationContext;
    private static Logger logger = LoggerFactory.getLogger("START");

    public static void main(String[] args) {
        long start = System.nanoTime();
        applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT);
        applicationContext.start();
        SpringContext.getGlobalService().onStart();
        new GameServer().connect();
        long end = System.nanoTime() - start;
        logger.info("服务器启动，耗时{}s", TimeUnit.NANOSECONDS.toSeconds(end));
    }
}