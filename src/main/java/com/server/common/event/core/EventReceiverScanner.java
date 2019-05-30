package com.server.common.event.core;

import com.server.common.event.anno.ReceiverAnno;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author yuxianming
 * @date 2019/5/29 11:34
 */
@Component
public class EventReceiverScanner implements BeanPostProcessor {

    @Autowired
    private EventBusManager eventBusManager;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
        Class<?> clz = bean.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ReceiverAnno.class)) {
                ReceiverDefinition definition = ReceiverDefinition.valueOf(bean, method);
                eventBusManager.regReceiver(definition.getClz(), definition);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        return bean;
    }
}
