package com.server.dispatcher;

import com.server.session.model.TSession;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author yuxianming
 * @date 2019/5/7 17:51
 * handler反射器，反射调用对应指令
 */
public class HandlerDefintion {

    /**
     * bean、方法、packet的class
     */
    private final Object bean;
    private final Method method;
    private final Class<?> clz;

    private HandlerDefintion(Object bean, Method method, Class<?> clz) {
        this.bean = bean;
        this.method = method;
        this.clz = clz;
    }

    /**
     * handler定义
     *
     * @param bean
     * @param method
     * @return
     */
    public static HandlerDefintion valueOf(Object bean, Method method) {

        Class<?> clz = null;
        Class<?>[] clzs = method.getParameterTypes();
        if (clzs.length != 2) {
            throw new IllegalArgumentException("class" + bean.getClass().getSimpleName() + "method" +
                    method.getName() + "必须只能有2个参数");
        }
        clz = clzs[1];

        return new HandlerDefintion(bean, method, clz);

    }

    /**
     * handler反射方法
     *
     * @param packet
     * @return
     */
    public Object invoke(Object packet) {
        method.setAccessible(true);
        Object result = null;
        ReflectionUtils.invokeMethod(method, bean, packet);
        return result;
    }

    public Object invoke(TSession session, Object packet) {
        method.setAccessible(true);
        Object result = null;
        ReflectionUtils.invokeMethod(method, bean, session, packet);
        return result;
    }

    public Object getBean() {
        return bean;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getClz() {
        return clz;
    }
}
