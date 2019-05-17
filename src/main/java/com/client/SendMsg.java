package com.client;

import com.server.command.packet.CM_GMcommand;
import com.server.server.message.MessageContent;
import io.netty.channel.ChannelHandlerContext;

import java.util.Scanner;

/**
 * @author yuxianming
 * @date 2019/5/16 22:51
 */
public class SendMsg implements Runnable {

    private ChannelHandlerContext ctx;
    private Scanner scanner;

    public SendMsg(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("输入客户端请求，指令如下：" + "\r\n"
                    + "1、注册账号： reg 账号id 账号名称 账号密码（举例： reg yxm 小明 pwd123）" + "\r\n"
                    + "2、登陆账号：  login 账号id 账号密码 （举例： login yxm pwd123）" + "\r\n"
                    + "3、切换地图： changemap 地图id（目前只有三张地图1001-1003 举例： changemap 1002）" + "\r\n"
                    + "4、打印账号所在地图信息： printMapInfo（举例： printMapInfo）" + "\r\n"
                    + "5、移动坐标： move x坐标 y坐标（举例： move 5 5）" + "\r\n"
                    + "6、打印账号信息：printAccInfo （举例： printAccInfo）"
            );
            String req = scanner.nextLine();
            CM_GMcommand cm = CM_GMcommand.vauleOf(req);
            MessageContent messageContent = new MessageContent(cm);
            ctx.writeAndFlush(messageContent);
        }
    }
}
