package com.server.command.service;

import com.client.clientframe.panel.CommandPanel;
import com.server.user.account.model.Account;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author yuxianming
 * @date 2019/5/21 11:38
 */
public class GmDefinition {

    /**
     * 方法名称（前端用 中文注解名称）
     */
    private String methodName;
    /**
     * 方法参数说明
     */
    private String paramDes;
    /**
     * 方法用途说明
     */
    private String methodDes;
    private Method method;
    private Object bean;

    private Class packetClz;


    private CommandPanel commandPanel;

    public static GmDefinition valueOf(Method method, Object bean, String methodName, String paramDes, String methodDes, Class packetClz) {
        GmDefinition gmHandlerDefinition = new GmDefinition();
        gmHandlerDefinition.bean = bean;
        gmHandlerDefinition.method = method;
        gmHandlerDefinition.methodName = methodName;
        gmHandlerDefinition.methodDes = methodDes;
        gmHandlerDefinition.paramDes = paramDes;
        gmHandlerDefinition.packetClz = packetClz;
        gmHandlerDefinition.commandPanel = new CommandPanel(method.getName(), methodName, paramDes, methodDes, packetClz);
        return gmHandlerDefinition;
    }

    public void invoke(Account account, Object... params) {
        method.setAccessible(true);
        ReflectionUtils.invokeMethod(method, bean, account, params);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParamDes() {
        return paramDes;
    }

    public void setParamDes(String paramDes) {
        this.paramDes = paramDes;
    }

    public String getMethodDes() {
        return methodDes;
    }

    public void setMethodDes(String methodDes) {
        this.methodDes = methodDes;
    }

    public CommandPanel getCommandPanel() {
        return commandPanel;
    }

    public void setCommandPanel(CommandPanel commandPanel) {
        this.commandPanel = commandPanel;
    }
}
