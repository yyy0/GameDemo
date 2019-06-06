package com.server.common.service;

import com.SpringContext;
import com.server.common.resource.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/4/29 15:00
 */
@Component
public class GlobalService {

    private static Logger logger = LoggerFactory.getLogger("ON-OFF");

    @Autowired
    private ResourceManager resourceManager;

    public void onStart() {

        logger.info("预加载resource资源");
        resourceManager.loadResource();
        logger.info("预加载地图资源");
        SpringContext.getWorldService().initMap();
        SpringContext.getEquipUpgradeService().initEquipUpResource();


    }
}
