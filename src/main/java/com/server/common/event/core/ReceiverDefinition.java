package com.server.common.event.core;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author yuxianming
 * @date 2019/5/29 11:12
 */
public class ReceiverDefinition {

    private final Object bean;
    private final Method method;
    private final Class<?> clz;

    public ReceiverDefinition(Object bean, Method method, Class<?> clz) {
        this.bean = bean;
        this.method = method;
        this.clz = clz;
    }

    public static ReceiverDefinition valueOf(Object bean, Method method) {
        Class<?>[] clazz = method.getParameterTypes();
        if (clazz.length != 1) {
            throw new IllegalArgumentException("必须只能有一个参数  " + "class:" + bean.getClass().getSimpleName() + "method:" + method.getName());
        }
        Class<?> claz = clazz[0];
        return new ReceiverDefinition(bean, method, claz);
    }

    public void invoke(Object receiver) {
        ReflectionUtils.makeAccessible(method);
        ReflectionUtils.invokeMethod(method, bean, receiver);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReceiverDefinition that = (ReceiverDefinition) o;
        return bean.equals(that.bean) &&
                method.equals(that.method) &&
                clz.equals(that.clz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bean, method, clz);
    }
}
