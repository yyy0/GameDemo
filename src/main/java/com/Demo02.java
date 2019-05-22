package com;

import com.server.command.anno.GmAnno;
import com.server.map.gm.MapGm;
import org.springframework.stereotype.Component;

/**
 * demo测试类2
 *
 * @author yuxianming
 * @date 2019/4/23 16:38
 */
@Component
public class Demo02 {

    public static void main(String[] args) {
        if (MapGm.class.isAnnotationPresent(GmAnno.class)) {
            System.out.println("1111");
        }

    }

}
