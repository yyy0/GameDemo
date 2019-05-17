package com.server.dispatcher;

import com.SpringContext;
import com.client.dispatcher.ClientHandlerAnno;
import com.client.dispatcher.ClientHandlerDefinition;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author yuxianming
 * @date 2019/5/7 17:44
 * handler扫描器 扫描所有带HandlerAnno注解的方法
 */
@Component
public class HandlerScanner implements BeanPostProcessor {
    @Autowired
    SpringContext springContext;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clz = bean.getClass();
        Method[] methods = clz.getDeclaredMethods();

        if (methods != null) {
            for (Method method : methods) {
                if (method.isAnnotationPresent(HandlerAnno.class)) {
                    HandlerDefintion defintion = HandlerDefintion.valueOf(bean, method);
                    SpringContext.getActionDispatcher().regHandlerDefintion(defintion.getClz(), defintion);
                } else if (method.isAnnotationPresent(ClientHandlerAnno.class)) {
                    ClientHandlerDefinition clientDefinition = ClientHandlerDefinition.valueOf(bean, method);
                    SpringContext.getActionDispatcher().regClientHandler(clientDefinition.getClz(), clientDefinition);
                }
            }
        }
        return bean;
    }


}
