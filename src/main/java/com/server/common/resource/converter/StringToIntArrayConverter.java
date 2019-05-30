package com.server.common.resource.converter;

import com.opencsv.bean.AbstractBeanField;

/**
 * @author yuxianming
 * @date 2019/5/23 1:17
 */
public class StringToIntArrayConverter extends AbstractBeanField<int[]> {
    @Override
    public int[] convert(String value) {

        String[] strArray = value.split(",");
        Integer[] array = new Integer[strArray.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(strArray[i]);
        }

        int[] intArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = array[i];
        }
        return intArray;

    }
}
