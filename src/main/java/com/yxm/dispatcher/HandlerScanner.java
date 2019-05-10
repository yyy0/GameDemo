package com.yxm.dispatcher;

import com.SpringContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author yuxianming
 * @date 2019/5/7 17:44
 * handler扫描器 扫描所有带HandlerAnno注解的方法
 */
@Component
public class HandlerScanner implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization开始初始化 处理handleranno！！");
        Class<?> clz = bean.getClass();
        Method[] methods = clz.getDeclaredMethods();

        if (methods != null) {
            for (Method method : methods) {
                HandlerAnno handlerAnno = AnnotationUtils.findAnnotation(method, HandlerAnno.class);
                if (method.isAnnotationPresent(HandlerAnno.class)) {
                    HandlerDefintion defintion = HandlerDefintion.valueOf(bean, method);
                    SpringContext.getActionDispatcher().regHandlerDefintion(defintion.getClz(), defintion);
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }
}
