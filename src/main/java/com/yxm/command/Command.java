package com.yxm.command;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yuxianming
 * @date 2019/4/24 17:55
 */
@Component
public class Command {

    public void login() {
    }

    public void test() {
        System.out.println("测试指令系统--1");
    }

    public void test2(String str) {
        System.out.println("测试指令系统--2，打印参数：" + str);
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class class1 = Command.class;
        Command command = new Command();
        Method method = class1.getDeclaredMethod("test");
        method.setAccessible(true);
        method.invoke(command);
    }
}
