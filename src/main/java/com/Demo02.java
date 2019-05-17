package com;

import com.server.tool.JsonUtils;
import com.server.user.account.model.Person;
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
        Person p = new Person("小明", 11);
        String json = JsonUtils.obj2json(p);
        System.out.println(json);
        Person p2 = JsonUtils.json2pojo(json, Person.class);
        System.out.println(p2);
        Person p3 = JsonUtils.json2pojo(json, Person.class);
        System.out.println(p3);

    }

}
