package com.client.clientframe.frame;

import com.client.dispatcher.ClientHandlerAnno;
import com.server.login.packet.CM_Reg;
import com.server.publicsystem.i18n.constant.I18Message;
import com.server.server.message.MessageContent;
import com.server.user.account.packet.SM_RegFail;
import com.server.user.account.packet.SM_RegSuccess;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author yuxianming
 * @date 2019/5/21 21:47
 */
@Component
public class RegFrame extends JFrame {

    private Channel channel;
    private JLabel accountNameLabel = new JLabel("昵称：");
    private JLabel accountLabel = new JLabel("账号：");
    private JLabel pwdLabel = new JLabel("密码：");
    private JTextField accountNameField = new JTextField();
    private JTextField accountField = new JTextField();
    private JPasswordField pwdField = new JPasswordField();
    private JButton regButton = new JButton("注册");

    private JLabel backLabel = new JLabel("注册提示：");
    private JTextArea printArea = new JTextArea();


    public void initFrame(Channel ch) {
        this.setTitle("注册界面");
        this.setLayout(null);

        this.channel = ch;

        accountNameLabel.setBounds(80, 40, 50, 50);
        accountNameField.setBounds(130, 40, 150, 50);
        accountLabel.setBounds(80, 100, 50, 50);
        accountField.setBounds(130, 100, 150, 50);

        pwdLabel.setBounds(80, 170, 50, 50);
        pwdField.setBounds(130, 170, 150, 50);
        regButton.setBounds(130, 240, 100, 30);
        backLabel.setBounds(10, 260, 80, 40);
        printArea.setBounds(10, 290, 380, 100);

        this.add(accountNameLabel);
        this.add(accountNameField);
        this.add(accountLabel);
        this.add(pwdLabel);
        this.add(pwdField);
        this.add(accountField);
        this.add(regButton);
        this.add(regButton);
        this.add(backLabel);
        this.add(printArea);
        this.addListener();

        this.setSize(400, 400);
        this.setLocation(450, 150);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void addListener() {
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String accountName = accountNameField.getText();
                String accountId = accountField.getText();
                String pwd = new String(pwdField.getPassword());
                if (accountName == "" || accountId == "" || pwd == "") {

                    return;
                }
                CM_Reg packet = CM_Reg.valueOf(accountId, accountName, pwd);
                MessageContent message = new MessageContent(packet);
                channel.writeAndFlush(message);
            }
        });
    }


    @ClientHandlerAnno
    public void clientRegSuccess(SM_RegSuccess packet) {
        String accountId = packet.getAccountId();
        String accountName = packet.getName();
        String message = "账号【" + accountId + "】注册成功！ 注册昵称：" + accountName;
        accountNameField.setText("");
        accountField.setText("");
        pwdField.setText("");
        if (printArea.getText() == "") {
            printArea.setText(message + "\r\n");
        } else {
            printArea.append(message + "\r\n");
        }

    }

    @ClientHandlerAnno
    public void clientRegFail(SM_RegFail packet) {
        String message = packet.getReq();
        printArea.append(I18Message.REG_FAIL + ":" + message + "\r\n");
        printArea.requestFocus();
    }
}
