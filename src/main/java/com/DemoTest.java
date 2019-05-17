package com;


import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * demo测试类
 */
public class DemoTest {

    private static final String APPLICATION_CONTEXT = "applicationContext.xml";
    private static ClassPathXmlApplicationContext applicationContext;

    public static void main(String[] args) {


        applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT);
        applicationContext.start();

        SpringContext.getGlobalService().onStart();



//        Account account=SpringContext.getAccountService().getAccount("yxm2");
//        SpringContext.getAccountService().printAccount(account);
//        account.setPwd("66666");
//        SpringContext.getAccountService().saveAccountInfo("yxm2",account);
//        SpringContext.getAccountService().printAccount(SpringContext.getAccountService().getAccount("yxm2"));
//        SpringContext.getAccountService().printAccount("server");
//        SpringContext.getAccountService().saveAccountInfo(account);
//        SpringContext.getLoginService().reg("xiaoming","小明","123");
//        SpringContext.getLoginService().login("server","123456");
//        SpringContext.getCommand().changeMap("server",1002);


    }
}
