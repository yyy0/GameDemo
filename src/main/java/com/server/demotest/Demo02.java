package com.server.demotest;

import com.server.common.resource.reader.CsvUtil;
import com.server.common.resource.reader.ResId;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


        List list = CsvUtil.getCsvData("resource/userResource.csv", UserResource.class);
        for (Object e : list) {
            System.out.println(e);
        }

        Map<Integer, Object> userResource = new HashMap<>();
        for (Object resource : list) {
            Class clz = resource.getClass();
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ResId.class)) {
                    String fieldName = field.getName();
                    String getFieldName = parGetName(fieldName);
                    try {
                        Method method = clz.getDeclaredMethod(getFieldName);
                        int getValue = (Integer) ReflectionUtils.invokeMethod(method, resource);
                        userResource.put(getValue, resource);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (Map.Entry<Integer, Object> entry : userResource.entrySet()) {
            System.out.println(entry.getKey() + "***" + entry.getValue());
        }


    }

}
