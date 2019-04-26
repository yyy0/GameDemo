package com;


import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoTest {

    private static final String APPLICATION_CONTEXT = "applicationContext.xml";
    private static ClassPathXmlApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT);
        applicationContext.start();


        SpringContext.getCommand().test();

//        Logger logger    = LoggerFactory.getLogger("输出日志");
//        logger.debug("输出debug");
//        logger.info("输出info");
//        logger.warn("输出warn");
//        logger.error("输出error");

    }
}
