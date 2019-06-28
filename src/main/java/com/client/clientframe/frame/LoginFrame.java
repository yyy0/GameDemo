package com.client.clientframe.frame;

import com.SpringContext;
import com.client.dispatcher.ClientHandlerAnno;
import com.server.login.packet.CM_Login;
import com.server.server.message.MessageContent;
import com.server.user.account.packet.SM_LoginSuccess;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登陆界面
 *
 * @author yuxianming
 * @date 2019/5/20 14:54
 */
@Component
public class LoginFrame extends JFrame {

    private Channel channel;

    private JLabel accountLabel = new JLabel("账号：");
    private JLabel pwdLabel = new JLabel("密码：");
    private JTextField accountField = new JTextField();
    private JPasswordField pwdField = new JPasswordField();
    private JButton loginButton = new JButton("登陆");
    private JButton regButton = new JButton("注册");


    public void initFrame(Channel channel) {
        this.setTitle("登陆界面");
        this.setLayout(null);
        this.channel = channel;
        accountLabel.setBounds(80, 100, 50, 50);
        accountField.setBounds(130, 100, 150, 50);
        regButton.setBounds(300, 100, 70, 50);
        pwdLabel.setBounds(80, 170, 50, 50);
        pwdField.setBounds(130, 170, 150, 50);
        loginButton.setBounds(130, 240, 100, 30);

        this.add(accountLabel);
        this.add(pwdLabel);
        this.add(pwdField);
        this.add(accountField);
        this.add(loginButton);
        this.add(regButton);

        this.addLisetener();
        this.setSize(400, 400);
        this.setLocation(450, 150);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void addLisetener() {
        /**
         * 登陆按钮监听 发送登陆协议
         */
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountId = accountField.getText();
                String accountPwd = new String(pwdField.getPassword());
                CM_Login packet = CM_Login.valueOf(accountId, accountPwd);
                System.out.println(accountId);
                MessageContent message = new MessageContent(packet);
                channel.writeAndFlush(message);
            }
        });
        /**
         * 注册按钮监听 打开注册界面
         */
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegFrame regFrame = SpringContext.getRegFrame();
                regFrame.initFrame(channel);
            }
        });
    }

    @ClientHandlerAnno
    public void clientLoginSuccess(SM_LoginSuccess packet) {
        this.dispose();
        CommandFrame commandFrame = SpringContext.getCommandFrame();
        commandFrame.initFrame(channel, packet.getAccountId());
    }

}
