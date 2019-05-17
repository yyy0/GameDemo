package com.server.common;

import com.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/4/29 15:00
 */
@Component
public class GlobalService {

    private static Logger logger = LoggerFactory.getLogger("ON-OFF");


    public void onStart() {

        logger.info("预加载地图资源");
        SpringContext.getWorldService().initMap();
        logger.info("预加载道具资源");
        SpringContext.getItemService().initItemResource();
    }
}
