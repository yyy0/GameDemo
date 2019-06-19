package com.server.gm.service;

import com.SpringContext;
import com.server.map.model.Grid;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yuxianming
 * @date 2019/4/24 17:55
 */
@Component
public class Command {

    /**
     * 注册指令
     *
     * @param accountId
     * @param name
     * @param pwd
     */
    public void reg(TSession session, String accountId, String name, String pwd) {
        SpringContext.getLoginService().reg(session, accountId, name, pwd);
    }


    /**
     * 登陆指令
     *
     * @param accountId
     * @param pwd
     */
    public void login(TSession session, String accountId, String pwd) {
        SpringContext.getLoginService().login(session, accountId, pwd);
    }

    /**
     * 切入地图指令
     * @param account
     * @param mapId
     */
    public void changeMap(Account account, Integer mapId) {
        SpringContext.getWorldService().changeMap(account, mapId);
    }

    /**
     * 打印指定账号当前地图信息
     */
    public void printMapInfo(Account account) {
        SpringContext.getWorldService().printMapInfo(account);
    }

    public void move(Account account, int gridX, int gridY) {
        Grid grid = Grid.valueOf(gridX, gridY);
        SpringContext.getWorldService().move(account, grid);
    }

    /**
     * 打印账号信息
     *
     * @param account
     */
    public void printAccInfo(Account account) {
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
