package com.server.gm.service;

import com.SpringContext;
import com.server.gm.constant.CommandConstant;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yuxianming
 * @date 2019/4/24 18:12
 */
@Component
public class CommandFacade {

    @Autowired
    private Command commandBean;
    Logger logger = LoggerFactory.getLogger("gm日志");

    private ConversionService conversionService = new DefaultConversionService();
    //private Command gm = new Command();

    public void doNewCommand(TSession session, String command) {
        {
            Account account = null;
            String accountId = null;
            if (session != null) {
                accountId = (String) session.getAttribute(SessionUtil.ACCOUNT_ID);
                if (accountId != null) {
                    account = SpringContext.getAccountService().getAccount(accountId);
                }
            }
            try {
                Pattern p = Pattern.compile("\\s+");
                Matcher m = p.matcher(command);
                command = m.replaceAll(" ");
                String methodName = null;
                //处理命令字符串，反射调用
                String[] strs = command.split(" ");
                methodName = strs[0];
                Map<String, GmDefinition> gmDefinitions = SpringContext.getGmDispatcher().getGmDefinitions();
                for (Map.Entry<String, GmDefinition> entry : gmDefinitions.entrySet()) {
                    if (methodName.equalsIgnoreCase(entry.getKey())) {
                        Method method = entry.getValue().getMethod();
                        method.setAccessible(true);
                        Object bean = entry.getValue().getBean();
                        invoke(account, bean, method, strs);
                        return;
                    }

                }
                logger.error("找不到对应指令" + command);

            } catch (Exception e) {
                logger.error(command, e);
            }

        }
    }

    public void doCommand(TSession session, String command) {

        Account account = null;
        String accountId = null;
        if (session != null) {
            accountId = (String) session.getAttribute(SessionUtil.ACCOUNT_ID);
            if (accountId != null) {
                account = SpringContext.getAccountService().getAccount(accountId);
            }
        }
        try {
            Pattern p = Pattern.compile("\\s+");
            Matcher m = p.matcher(command);
            command = m.replaceAll(" ");
            String methodName = null;
            //处理命令字符串，反射调用
            String[] strs = command.split(" ");
            methodName = strs[0];
            Method[] methods = Command.class.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equalsIgnoreCase(methodName) && methodName.equalsIgnoreCase(CommandConstant.REG_COMMAND)) {
                    method.setAccessible(true);
                    System.out.println("使用注册指令" + method.getName());
                    ReflectionUtils.invokeMethod(method, commandBean, session, strs[1], strs[2], strs[3]);
                    return;
                }
                if (method.getName().equalsIgnoreCase(methodName) && methodName.equalsIgnoreCase(CommandConstant.LOGIN_COMMAND)) {
                    method.setAccessible(true);
                    System.out.println("使用登陆指令" + method.getName());
                    ReflectionUtils.invokeMethod(method, commandBean, session, strs[1], strs[2]);
                    return;
                }
                if (method.getName().equalsIgnoreCase(methodName)) {
                    method.setAccessible(true);
                    System.out.println("使用gm指令" + method.getName());
                    invoke(account, commandBean, method, strs);
                    return;
                }
            }
            logger.error("找不到对应指令" + command);
        } catch (Exception e) {
            logger.error(command, e);
        }

    }

    public void invoke(Account account, Object bean, Method method, String[] split) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] paramType = method.getParameterTypes();
        switch (paramType.length - 1) {
            case 0: {
                method.invoke(bean, account);
                break;
            }
            case 1: {
                Class<?> class1 = paramType[1];
                method.invoke(bean, account, conversionService.convert(split[1], class1));
                break;
            }
            case 2: {
                Class<?> class1 = paramType[1];
                Class<?> class2 = paramType[2];
                method.invoke(bean, account, conversionService.convert(split[1], class1), conversionService.convert(split[2], class2));
                break;
            }
            case 3: {
                Class<?> class1 = paramType[1];
                Class<?> class2 = paramType[2];
                Class<?> class3 = paramType[3];
                method.invoke(bean, account, conversionService.convert(split[1], class1), conversionService.convert(split[2], class2),
                        conversionService.convert(split[3], class3));
                break;
            }
            case 4: {
                Class<?> class1 = paramType[1];
                Class<?> class2 = paramType[2];
                Class<?> class3 = paramType[3];
                Class<?> class4 = paramType[4];
                method.invoke(bean, account, conversionService.convert(split[1], class1), conversionService.convert(split[2], class2),
                        conversionService.convert(split[3], class3), conversionService.convert(split[4], class4));
                break;
            }
            case 5: {
                Class<?> class1 = paramType[1];
                Class<?> class2 = paramType[2];
                Class<?> class3 = paramType[3];
                Class<?> class4 = paramType[4];
                Class<?> class5 = paramType[5];
                method.invoke(bean, account, conversionService.convert(split[1], class1), conversionService.convert(split[2], class2),
                        conversionService.convert(split[3], class3), conversionService.convert(split[4], class4), conversionService.convert(split[5], class5));
                break;
            }
            default:
                break;
        }
    }

}
