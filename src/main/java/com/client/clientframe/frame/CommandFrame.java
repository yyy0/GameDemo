package com.client.clientframe.frame;

import com.SpringContext;
import com.client.clientframe.panel.ModelPanel;
import com.client.dispatcher.ClientHandlerAnno;
import com.server.command.packet.CM_GMcommand;
import com.server.command.service.GmDefinition;
import com.server.common.resource.ResourceManager;
import com.server.map.packet.SM_AccountMove;
import com.server.map.packet.SM_ChangeMap;
import com.server.publicsystem.i18n.packet.SM_Notify_Message;
import com.server.publicsystem.i18n.resource.I18NResource;
import com.server.server.message.MessageContent;
import com.server.user.account.packet.SM_AccountInfo;
import com.server.user.item.model.AbstractItem;
import com.server.user.item.packet.SM_BagInfo;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * 游戏指令界面
 *
 * @author yuxianming
 * @date 2019/5/20 15:43
 */
@Component
public class CommandFrame extends JFrame {

    private static final String APPLICATION_CONTEXT = "applicationContext.xml";
    private static ClassPathXmlApplicationContext applicationContext;
    private Channel channel;
    @Autowired
    private ResourceManager resourceManager;

    public static Logger logger = LoggerFactory.getLogger(CommandFrame.class);

    Container container = this.getContentPane();

    /**
     * 功能模块指令下拉框
     */
    JComboBox modelBox = new JComboBox();

    /**
     * 指令发送按钮
     */
    JButton sendButton = new JButton("发送");

    /**
     * 指令发送输入框
     */
    JTextField commandField = new JTextField();

    JLabel printLabel = new JLabel("输出游戏信息");

    JButton clearAreaButton = new JButton("清空信息");

    /**
     * 回显信息输出框
     */
    JTextArea printArea = new JTextArea();

    /**
     * 回显信息滑动框
     */
    JScrollPane scrollPane = new JScrollPane();

    public void initFrame(Channel channel) {
        this.setTitle("游戏指令界面");
        this.channel = channel;
        container.setLayout(null);
        printArea.setLineWrap(true);

        //功能模块下拉框
        modelBox.addItem("全部指令");
        Map<String, Map<String, GmDefinition>> gmModelDefinitions = SpringContext.getGmDispatcher().getGmModelDefinitions();
        for (String keyName : gmModelDefinitions.keySet()) {
            modelBox.addItem(keyName);
        }

        /** 功能panel */
        JScrollPane modelPanel = new ModelPanel(channel);

        modelBox.setBounds(20, 10, 150, 50);
        modelPanel.setBounds(20, 70, 320, 330);
        commandField.setBounds(20, 410, 210, 40);
        sendButton.setBounds(250, 410, 70, 40);
        printLabel.setBounds(350, 20, 80, 20);
        scrollPane.setBounds(350, 70, 220, 400);
        printArea.setBounds(350, 70, 220, 400);
        clearAreaButton.setBounds(470, 20, 100, 40);

        //添加button监听器
        addListener();

        // TODO  modelBox将在后续完成 显示指定gm功能模块
//        container.add(modelBox);
        container.add(modelPanel);
        container.add(commandField);
        container.add(sendButton);
        container.add(printLabel);
        container.add(scrollPane);
        scrollPane.setViewportView(printArea);
        container.add(clearAreaButton);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setLocation(450, 150);
    }


    public static void main(String[] args) {

        applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT);
        applicationContext.start();
        new CommandFrame();
    }

    @ClientHandlerAnno
    public void clientPrintAccount(SM_AccountInfo packet) {
        System.out.println("打印账号信息");
        String message = packet.toString();
        printArea.append(message + "\r\n");
    }

    public void addListener() {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = commandField.getText();
                if (command == "") {
                    return;
                }
                CM_GMcommand packet = CM_GMcommand.vauleOf(command);
                MessageContent message = new MessageContent(packet);
                channel.writeAndFlush(message);
            }
        });

        clearAreaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printArea.setText("");
            }
        });
    }

    @ClientHandlerAnno
    public void clientChangeMap(SM_ChangeMap packet) {

        String message = packet.toString();
        printArea.append(message + "\r\n");
    }

    @ClientHandlerAnno
    public void clientMove(SM_AccountMove packet) {
        String message = packet.toString();
        printArea.append(message + "\r\n");
    }

    /**
     * 打印背包消息
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void printBagInfo(SM_BagInfo packet) {
        AbstractItem[] items = packet.getItemData();
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                return;
            }
            printArea.append(items[i].toString() + "\r\n");
        }
    }

    /**
     * 打印提示消息
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void printNotifyMessage(SM_Notify_Message packet) {
        int id = packet.getI18Id();

        Map<Integer, Object> i18Resources = resourceManager.getResources(I18NResource.class.getSimpleName());
        I18NResource resource = (I18NResource) i18Resources.get(id);
        if (resource == null) {
            logger.error("找不到对应配置id：{}" + id);
        }
        String message = resource.getValue();
        printArea.append(message + "\r\n");
    }


}
