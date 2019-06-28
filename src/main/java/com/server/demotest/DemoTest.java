package com.server.demotest;


import com.server.rank.model.FightPowerInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * demo测试类
 *
 * @author yuxianming
 * @date 2019/4/23 16:38
 */
public class DemoTest {


    public static void main(String[] args) {
        Random random = new Random();

        long start = System.nanoTime();
        System.out.println(start);
        ArrayList<FightPowerInfo> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            int j = random.nextInt(10000);
            FightPowerInfo info = new FightPowerInfo("第" + i + "个玩家", j);
//            System.out.println("名称：" +info.getAccountId() + " 战力: " + info.getFightPower());
            list.add(info);
        }
        long end = System.nanoTime() - start;
        System.out.println(end);
        System.out.println("生成10000个随机对象，耗时：" + TimeUnit.NANOSECONDS.toSeconds(end));
        long start2 = System.nanoTime();
        Collections.sort(list);
        long end2 = System.nanoTime() - start2;
        System.out.println("排序10000个随机对象，耗时：" + TimeUnit.NANOSECONDS.toSeconds(end2));
        for (FightPowerInfo info : list) {
//            System.out.println("名称：" +info.getAccountId() + " 战力: " + info.getFightPower());
        }

    }

}
