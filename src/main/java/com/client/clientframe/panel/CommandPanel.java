package com.client.clientframe.panel;

import com.client.clientframe.constant.ClientFrameConstant;
import com.server.command.packet.CM_GMcommand;
import com.server.server.message.MessageContent;
import io.netty.channel.Channel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
     * 发送按钮
     */
    private JButton sendButton = new JButton(ClientFrameConstant.SEND_BUTTON);

    public CommandPanel(String methodName, String labelName, String paramDes, String commandDes) {
        this.methodName = methodName;
        this.labelName = labelName;
        this.paramDes = paramDes;
        this.commandDes = commandDes;
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
                String req;
                if (null != textField) {
                    req = methodName + " " + textField.getText();
                } else {
                    req = methodName;
                }
                CM_GMcommand command = CM_GMcommand.vauleOf(req);
                MessageContent message = new MessageContent(command);
                channel.writeAndFlush(message);
            }
        });
    }

    public void initPanel(Channel ch) {
        this.channel = ch;
        addListener();
    }


}
