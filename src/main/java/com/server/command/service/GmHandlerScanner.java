//package com.server.command.service;
//
//import com.SpringContext;
//import com.server.command.anno.GmAnno;
//import com.server.command.anno.GmMethod;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author yuxianming
// * @date 2019/5/21 11:46
// */
//@Component
//public class GmHandlerScanner implements BeanPostProcessor {
//    @Autowired
//    SpringContext springContext;
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
//
//        Class<?> clz=bean.getClass();
//
//
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
//        return bean;
//    }
//}
