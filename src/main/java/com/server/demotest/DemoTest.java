package com.server.demotest;


import com.server.tool.ObjectByteUtil;
import com.server.user.item.model.AbstractItem;

/**
 * demo测试类
 * @author yuxianming
 * @date 2019/4/23 16:38
 */
public class DemoTest {

    public static void main(String[] args) {
        AbstractItem item = new AbstractItem();
        item.setObjectId(11L);
        byte[] bytes = ObjectByteUtil.objectToByteArray(item);
        AbstractItem item1 = (AbstractItem) ObjectByteUtil.byteArrayToObject(bytes);
        System.out.println(item1.getObjectId());
    }

}
