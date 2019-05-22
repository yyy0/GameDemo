package com.server.dispatcher;

import com.client.dispatcher.ClientHandlerDefinition;
import com.server.session.model.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 协议分发处理器
 *
 * @author yuxianming
 * @date 2019/5/10 0:02
 */
@Component
public class ActionDispatcher {

    private static Logger logger = LoggerFactory.getLogger(ActionDispatcher.class);

    private Map<Class<?>, HandlerDefintion> handlerMap = new HashMap<Class<?>, HandlerDefintion>();
    private Map<Class<?>, ClientHandlerDefinition> ClientHandlerMap = new HashMap<Class<?>, ClientHandlerDefinition>();

    public void doClientHandle(Object packet) {
        ClientHandlerDefinition defintion = ClientHandlerMap.get(packet.getClass());
        if (defintion == null) {
            throw new NullPointerException("没找到对应的ClientHandlerDefinition:" + packet.getClass().getSimpleName());
        }
        defintion.invoke(packet);
    }

    public void doHandle(Object packet) {
        HandlerDefintion defintion = handlerMap.get(packet.getClass());
        if (defintion == null) {
            throw new NullPointerException("没找到对应的handlerDefinition:" + packet.getClass().getSimpleName());
        }
        defintion.invoke(packet);
    }

    public void doHandle(TSession session, Object packet) {
        HandlerDefintion defintion = handlerMap.get(packet.getClass());
        if (defintion == null) {
            throw new NullPointerException("没找到对应的handlerDefinition:" + packet.getClass().getSimpleName());
        }
        defintion.invoke(session, packet);
    }

    public void regHandlerDefintion(Class<?> clz, HandlerDefintion definition) {
        HandlerDefintion handlerDefintion = handlerMap.put(clz, definition);
        if (handlerDefintion != null) {
            throw new IllegalArgumentException("太多handler处理packet了：" + clz.getSimpleName());
        }
    }

    public void regClientHandler(Class<?> clz, ClientHandlerDefinition definition) {
        ClientHandlerDefinition clientHandlerDefinition = ClientHandlerMap.put(clz, definition);
        if (clientHandlerDefinition != null) {
            throw new IllegalArgumentException("太多handler处理packet了：" + clz.getSimpleName());
        }
    }
}
