package com.server.demotest;


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * demo测试类
 * @author yuxianming
 * @date 2019/4/23 16:38
 */
public class DemoTest {


    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getHostAddress());

    }

}
