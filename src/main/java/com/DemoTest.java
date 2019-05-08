package com;


import com.yxm.user.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoTest {

    private static final String APPLICATION_CONTEXT = "applicationContext.xml";
    private static ClassPathXmlApplicationContext applicationContext;
    Logger logger = LoggerFactory.getLogger("entity");


    public static void main(String[] args) {


        applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT);
        applicationContext.start();

        SpringContext.getGlobalService().onStart();


//        Account account=Account.valueOf("yxm8","小赵","xiaozhao123");
//        SpringContext.getAccountService().createAccount(account);
        Account account1 = SpringContext.getAccountService().getAccount("yxm8");
        SpringContext.getAccountService().printAccount(account1);
        Account account2 = SpringContext.getAccountService().getAccount("yxm8");
        SpringContext.getAccountService().printAccount(account2);


//        Account account=SpringContext.getAccountService().getAccount("yxm2");
//        SpringContext.getAccountService().printAccount(account);
//        account.setPwd("66666");
//        SpringContext.getAccountService().saveAccountInfo("yxm2",account);
//        SpringContext.getAccountService().printAccount(SpringContext.getAccountService().getAccount("yxm2"));
//        SpringContext.getAccountService().printAccount("yxm");
//        SpringContext.getAccountService().saveAccountInfo(account);
//        SpringContext.getLoginService().reg("xiaoming","小明","123");
//        SpringContext.getLoginService().login("yxm","123456");
//        SpringContext.getCommand().changeMap("yxm",1002);



    }
}
