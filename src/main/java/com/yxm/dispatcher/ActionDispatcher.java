package com.yxm.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/10 0:02
 */
@Component
public class ActionDispatcher {

    private static Logger logger = LoggerFactory.getLogger(ActionDispatcher.class);

    private Map<Class<?>, HandlerDefintion> handlerMap = new HashMap<Class<?>, HandlerDefintion>();

    public void doHandle(Object packet) {
        HandlerDefintion defintion = handlerMap.get(packet.getClass());
        if (defintion == null) {
            throw new NullPointerException("没找到对应的handlerDefinition:" + packet.getClass().getSimpleName());
        }
        Object res = defintion.invoke(packet);
    }

    public void regHandlerDefintion(Class<?> clz, HandlerDefintion defintion) {
        HandlerDefintion handlerDefintion = handlerMap.put(clz, defintion);
        if (handlerDefintion != null) {
            throw new IllegalArgumentException("太多handler处理packet了：" + clz.getSimpleName());
        }
    }
}
