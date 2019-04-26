package com;

import com.yxm.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/4/23 16:38
 */
@Component
public class Demo02 {
    @Autowired
    private Command command;

    public static void main(String[] args) {
        new Demo02().test();
    }

    public void test() {

        if (null == command) {
            System.out.println("注入失败");
        } else {
            command.test();
        }
    }
}
