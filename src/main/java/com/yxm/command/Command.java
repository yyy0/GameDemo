package com.yxm.command;

import com.SpringContext;
import com.yxm.map.Grid;
import com.yxm.user.account.Account;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yuxianming
 * @date 2019/4/24 17:55
 */
@Component
public class Command {



    public void test() {
        System.out.println("测试指令系统--1");
    }

    public void test2(String str) {
        System.out.println("测试指令系统--2，打印参数：" + str);
    }

    /**
     * 注册指令
     *
     * @param accountId
     * @param name
     * @param pwd
     */
    public void reg(String accountId, String name, String pwd) {
        SpringContext.getLoginService().reg(accountId, name, pwd);
    }


    /**
     * 登陆指令
     *
     * @param accountId
     * @param pwd
     */
    public void login(String accountId, String pwd) {
        SpringContext.getLoginService().login(accountId, pwd);
    }

    /**
     * 切入地图指令
     *
     * @param accountId
     * @param mapId
     */
    public void changeMap(String accountId, Integer mapId) {
        Account account = SpringContext.getAccountService().getAccount(accountId);
        SpringContext.getWorldService().changeMap(account, mapId);
    }

    /**
     * 打印指定账号当前地图信息
     */
    public void printMapInfo(String accountId) {
        Account account = SpringContext.getAccountService().getAccount(accountId);
        SpringContext.getWorldService().printMapInfo(account);
    }

    public void move(String accountId, int gridX, int gridY) {
        Account account = SpringContext.getAccountService().getAccount(accountId);
        Grid grid = Grid.valueOf(gridX, gridY);
        SpringContext.getWorldService().move(account, grid);
    }

    /**
     * 打印账号信息
     *
     * @param accountId
     */
    public void printAccInfo(String accountId) {
        Account account = SpringContext.getAccountService().getAccount(accountId);
        SpringContext.getAccountService().printAccount(account);
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class class1 = Command.class;
        Command command = new Command();
        Method method = class1.getDeclaredMethod("test");
        method.setAccessible(true);
        method.invoke(command);
    }
}
