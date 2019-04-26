package com.yxm.command;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yuxianming
 * @date 2019/4/24 18:12
 */
@Component
public class CommandFacade {

    @Autowired
    private Command command;
    Logger logger = LoggerFactory.getLogger("gm日志");
    private ConversionService conversionService = new GenericConversionService();
    //private Command command = new Command();

    public void doCommand(ChannelHandlerContext ctx, Object msg) throws InvocationTargetException, IllegalAccessException {


        String command = (String) msg;
        System.out.println("开始执行指令：" + msg);
        Pattern p = Pattern.compile("\\s+");
        Matcher m = p.matcher(command);
        command = m.replaceAll(" ");
        String methodName = null;
        //处理命令字符串，反射调用
        String[] strs = command.split(" ");
        methodName = strs[0];
        Method[] methods = Command.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase(methodName)) {
                method.setAccessible(true);
                System.out.println("使用gm指令" + methodName);
                invoke(method, strs);
                return;
            }
        }
        logger.error("找不到对应指令");

    }

    public void invoke(Method method, String[] split) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] paramType = method.getParameterTypes();
        switch (paramType.length) {
            case 0: {
                method.invoke(command);
                break;
            }
            case 1: {
                Class<?> class1 = paramType[0];
                method.invoke(command, conversionService.convert(split[1], class1));
                break;
            }
            case 2: {
                Class<?> class1 = paramType[1];
                method.invoke(command, conversionService.convert(split[1], class1));
                break;
            }
            case 3: {
                Class<?> class1 = paramType[2];
                method.invoke(command, conversionService.convert(split[1], class1));
                break;
            }
            case 4: {
                Class<?> class1 = paramType[3];
                method.invoke(command, conversionService.convert(split[1], class1));
                break;
            }
            case 5: {
                Class<?> class1 = paramType[4];
                method.invoke(command, conversionService.convert(split[1], class1));
                break;
            }
        }
    }

}