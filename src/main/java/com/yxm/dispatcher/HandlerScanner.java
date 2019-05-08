package com.yxm.dispatcher;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * @author yuxianming
 * @date 2019/5/7 17:44
 * handler扫描器 扫描所有带HandlerAnno注解的方法
 */
public class HandlerScanner implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clz = bean.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(HandlerAnno.class)) {
                HandlerDefintion defintion = HandlerDefintion.valueOf(bean, method);

            }
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }
}
