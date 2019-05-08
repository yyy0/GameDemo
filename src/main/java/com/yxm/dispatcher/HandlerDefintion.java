package com.yxm.dispatcher;

import java.lang.reflect.Method;

/**
 * @author yuxianming
 * @date 2019/5/7 17:51
 * handler反射器，反射调用对应指令
 */
public class HandlerDefintion {

    private Object bean;
    private Method method;

    public static HandlerDefintion valueOf(Object bean, Method method) {
        HandlerDefintion handlerDefintion = new HandlerDefintion();
        handlerDefintion.setBean(bean);
        handlerDefintion.setMethod(method);
        return handlerDefintion;

    }

    public Object invoke() {
        return null;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
