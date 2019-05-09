package com.yxm.user.account.model;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/6 23:18
 * <p>
 * 测试类
 */


public class Person implements Serializable {

    public int id;
    public String name;
    public int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
