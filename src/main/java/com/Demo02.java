package com;

import com.yxm.tool.JsonUtils;
import com.yxm.user.account.Person;
import org.springframework.stereotype.Component;

/**
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
