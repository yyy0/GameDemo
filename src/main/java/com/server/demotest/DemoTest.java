package com.server.demotest;


import com.server.tool.ObjectByteUtil;
import com.server.user.equipment.constant.EquipmentPosition;
import com.server.user.equipment.model.Equipment;
import com.server.user.equipment.packet.SM_EquipsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * demo测试类
 * @author yuxianming
 * @date 2019/4/23 16:38
 */
public class DemoTest {

    private static final String APPLICATION_CONTEXT = "applicationContext.xml";
    private static ClassPathXmlApplicationContext applicationContext;
    private static Logger logger = LoggerFactory.getLogger("START");

    public static void main(String[] args) {

//        applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT);
//        applicationContext.start();
//        SpringContext.getGlobalService().onStart();
        Map<EquipmentPosition, Equipment> equipments = new HashMap<>();
        equipments.put(EquipmentPosition.WEAPON, new Equipment());
        SM_EquipsInfo packet = SM_EquipsInfo.valueOf(equipments);
        byte[] bytes = ObjectByteUtil.objectToByteArray(packet);
        SM_EquipsInfo packet2 = (SM_EquipsInfo) ObjectByteUtil.byteArrayToObject(bytes);
        Map<Integer, Equipment> equipmentMap = packet2.getEquipmentMap();
    }

}
