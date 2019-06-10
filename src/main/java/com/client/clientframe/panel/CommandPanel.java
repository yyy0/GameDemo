package com.client.clientframe.panel;

import com.client.clientframe.constant.ClientFrameConstant;
import com.server.server.message.MessageContent;
import io.netty.channel.Channel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.ReflectionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yuxianming
 * @date 2019/5/20 17:20
 */
public class CommandPanel extends JPanel {


    private Channel channel;
    private JTextField textField;

    /**
     * 方法名称（服务端用）
     */
    private String methodName;

    /**
     * 方法名称（前端用）
     */
    private String labelName;

    /**
     * 方法参数说明
     */
    private String paramDes;

    /**
     * 方法用途说明
     */
    private String commandDes;
    /**
     * 协议class
     */
    private Class packetClz;

    /**
     * 发送按钮
     */
    private JButton sendButton = new JButton(ClientFrameConstant.SEND_BUTTON);

    private ConversionService conversionService = new DefaultConversionService();

    public CommandPanel(String methodName, String labelName, String paramDes, String commandDes, Class packetClz) {
        this.methodName = methodName;
        this.labelName = labelName;
        this.paramDes = paramDes;
        this.commandDes = commandDes;
        this.packetClz = packetClz;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(ClientFrameConstant.COMMAND_PANEL_WIDTH, ClientFrameConstant.COMMAND_PANEL_HEIGHT));
        this.setBackground(Color.GRAY);
        JLabel label = new JLabel(this.labelName);
        this.add(label, BorderLayout.WEST);
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanel.setBackground(Color.GRAY);

        // 命令输入框
        if (paramDes != null && !"".equals(paramDes)) {
            textField = new JTextField();
            textField.setColumns(8);
            JLabel paramDesLabel;

            if (paramDes.length() > ClientFrameConstant.LabelLineLength) {
                String str1 = paramDes.substring(0, ClientFrameConstant.LabelLineLength - 1);
                String str2 = paramDes.substring(ClientFrameConstant.LabelLineLength - 1, paramDes.length());
                paramDesLabel = new JLabel("<html><body>" + str1 + "<br>" + str2 + "<body></html>");
            } else {
                paramDesLabel = new JLabel(paramDes);
            }

            jPanel.add(textField);
            jPanel.add(paramDesLabel);
        }
        if (commandDes != null && !commandDes.equals("")) {
            JLabel desLabel = new JLabel(commandDes);
            jPanel.add(desLabel);
        }
        //发送按钮
        sendButton.setSize(40, 40);
        this.add(jPanel, BorderLayout.CENTER);
        this.add(sendButton, BorderLayout.EAST);

    }

    public void addListener() {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Object packet;
                    if (null == textField) {
                        packet = packetClz.newInstance();
                        MessageContent message = new MessageContent(packet);
                        channel.writeAndFlush(message);
                        return;
                    }
                    String reqArea = textField.getText();
                    Pattern p = Pattern.compile("\\s+");
                    Matcher m = p.matcher(reqArea);
                    reqArea = m.replaceAll(" ");
                    //处理命令字符串，反射生成对应协议
                    String[] strs = reqArea.split(" ");
                    Method[] methods = packetClz.getDeclaredMethods();
                    for (Method method : methods) {
                        if (method.getName().equals(ClientFrameConstant.VALUEOF)) {
                            packet = invoke(method, strs);
                            MessageContent message = new MessageContent(packet);
                            channel.writeAndFlush(message);
                            return;
                        }
                    }
                    throw new RuntimeException("该协议找不到对应的valueOf方法：" + packetClz.getSimpleName());

                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                }


            }
        });
    }

    public Object invoke(Method method, String[] split) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] paramType = method.getParameterTypes();
        ReflectionUtils.makeAccessible(method);
        switch (paramType.length) {
            case 0: {
                return ReflectionUtils.invokeMethod(method, null);
            }
            case 1: {
                Class<?> class1 = paramType[0];
                return method.invoke(null, conversionService.convert(split[0], class1));
            }
            case 2: {
                Class<?> class1 = paramType[0];
                Class<?> class2 = paramType[1];
                return method.invoke(null, conversionService.convert(split[0], class1), conversionService.convert(split[1], class2));
            }
            case 3: {
                Class<?> class1 = paramType[0];
                Class<?> class2 = paramType[1];
                Class<?> class3 = paramType[2];
                return method.invoke(null, conversionService.convert(split[0], class1), conversionService.convert(split[1], class2),
                        conversionService.convert(split[2], class3));
            }
            case 4: {
                Class<?> class1 = paramType[0];
                Class<?> class2 = paramType[1];
                Class<?> class3 = paramType[2];
                Class<?> class4 = paramType[3];
                return method.invoke(null, conversionService.convert(split[0], class1), conversionService.convert(split[1], class2),
                        conversionService.convert(split[2], class3), conversionService.convert(split[3], class4));
            }
            case 5: {
                Class<?> class1 = paramType[0];
                Class<?> class2 = paramType[1];
                Class<?> class3 = paramType[2];
                Class<?> class4 = paramType[3];
                Class<?> class5 = paramType[4];
                return method.invoke(null, conversionService.convert(split[0], class1), conversionService.convert(split[1], class2),
                        conversionService.convert(split[2], class3), conversionService.convert(split[3], class4), conversionService.convert(split[4], class5));
            }
            default:
                return null;
        }
    }

    public void initPanel(Channel ch) {
        this.channel = ch;
        addListener();
    }

    public Class getPacketClz() {
        return packetClz;
    }

    public void setPacketClz(Class packetClz) {
        this.packetClz = packetClz;
    }
}
