package com.server.dispatcher;

import com.client.dispatcher.ClientHandlerAnno;
import com.client.dispatcher.ClientHandlerDefinition;
import com.server.command.anno.GmAnno;
import com.server.command.anno.GmMethod;
import com.server.command.service.GmDefinition;
import com.server.command.service.GmDispatcher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/7 17:44
 * handler扫描器 扫描所有带HandlerAnno注解的方法
 */
@Component
public class HandlerScanner implements BeanPostProcessor {

    @Autowired
    ActionDispatcher actionDispatcher;

    @Autowired
    GmDispatcher dispatcher;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clz = bean.getClass();
        Method[] methods = clz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(HandlerAnno.class)) {
                HandlerDefintion definition = HandlerDefintion.valueOf(bean, method);
                actionDispatcher.regHandlerDefintion(definition.getClz(), definition);
            } else if (method.isAnnotationPresent(ClientHandlerAnno.class)) {
                ClientHandlerDefinition clientDefinition = ClientHandlerDefinition.valueOf(bean, method);
                System.out.println(clientDefinition.getClz().getName());
                actionDispatcher.regClientHandler(clientDefinition.getClz(), clientDefinition);
            }
        }

        if (clz.isAnnotationPresent(GmAnno.class)) {
            Map<String, GmDefinition> gmDefinitions = new HashMap<>();
            for (Method method : methods) {
                if ((method.isAnnotationPresent(GmMethod.class))) {
                    //gm方法名称（前端用）
                    String methodName = method.getAnnotation(GmMethod.class).name();
                    //gm方法参数
                    String methodParam = method.getAnnotation(GmMethod.class).param();
                    //gm方法描述
                    String methodDes = method.getAnnotation(GmMethod.class).des();
                    //协议class
                    Class packetClz = method.getAnnotation(GmMethod.class).clz();
                    GmDefinition gmHandlerDefinition = GmDefinition.valueOf(method, bean, methodName, methodParam, methodDes, packetClz);
                    // 客户端注册
                    gmDefinitions.put(method.getName(), gmHandlerDefinition);
                    // 服务端注册
                    dispatcher.regGmDefinition(method.getName(), gmHandlerDefinition);
                }
            }
            String classAnnoName = clz.getAnnotation(GmAnno.class).title();
            dispatcher.regGmClassDefinition(classAnnoName, gmDefinitions);
        }

        return bean;
    }


}
