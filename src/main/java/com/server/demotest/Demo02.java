package com.server.demotest;

import org.springframework.stereotype.Component;

/**
 * demo测试类2
 *
 * @author yuxianming
 * @date 2019/4/23 16:38
 */
@Component
public class Demo02 {


    /**
     * 获取某属性的 get方法
     *
     * @param fieldName
     * @return String
     */
    public static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        char[] charArray = fieldName.toCharArray();
        charArray[0] -= 32;
        String newName = String.valueOf(charArray);
        return "get" + newName;
    }

    public static void main(String[] args) {


    }

}
