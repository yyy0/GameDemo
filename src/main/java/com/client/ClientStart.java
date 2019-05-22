//package com.client;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
///**
// * @author yuxianming
// * @date 2019/4/23 21:08
// */
//public class ClientStart {
//    public static void main(String[] args) {
//
//        //建立一个窗口
//        JFrame jf = new JFrame("DEMO客户端");
//        //使用流布局
//        FlowLayout fl = new FlowLayout();
//        //修改布局管理
//        jf.setLayout(fl);
//        final TextField textField=new TextField(20);
//        final TextArea textArea=new TextArea(10,40);
//        final JButton button = new JButton("发送信息");
//
//        jf.add(button);
//        jf.add(textField);
//        jf.add(textArea);
//        jf.setSize(600,300);
//        jf.setLocation(300,200);
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String str=textField.getText().trim();
//                textField.setText("");
//                textArea.append(str+"\r\n");
//                textField.requestFocus();
//            }
//        });
//
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jf.setVisible(true);
//    }
//}
