package com.client.dispatcher;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 客户端handler反射器，反射调用对应指令
 *
 * @author yuxianming
 * @date 2019/5/17 16:41
 */
public class ClientHandlerDefinition {

    /**
     * bean、方法、packet的class
     */
    private final Object bean;
    private final Method method;
    private final Class<?> clz;

    private ClientHandlerDefinition(Object bean, Method method, Class<?> clz) {
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
    public static ClientHandlerDefinition valueOf(Object bean, Method method) {

        Class<?> clz = null;
        Class<?>[] clzs = method.getParameterTypes();
        if (clzs.length != 1) {
            throw new IllegalArgumentException("class" + bean.getClass().getSimpleName() + "method" +
                    method.getName() + "必须只能有1个参数");
        }
        clz = clzs[0];

        return new ClientHandlerDefinition(bean, method, clz);

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
