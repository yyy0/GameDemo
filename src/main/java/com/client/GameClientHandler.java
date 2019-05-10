package com.client;

import com.yxm.login.packet.CM_Login;
import com.yxm.login.packet.CM_Reg;
import com.yxm.server.message.MessageContent;
import com.yxm.tool.ObjectByteUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author yuxianming
 * @date 2019/4/24 12:30
 */
public class GameClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * Creates a client-side handler.
     */
//    public GameClientHandler() {
//        firstMessage = Unpooled.buffer(GameClient.SIZE);
//        for (int i = 0; i < firstMessage.capacity(); i++) {
//            firstMessage.writeByte((byte) i);
//        }
//    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        CM_Reg req1 = CM_Reg.valueOf("jerry", "杰瑞", "jerry123");
        byte[] bytes1 = ObjectByteUtil.objectToByteArray(req1);
        MessageContent message1 = new MessageContent(bytes1.length, req1);
        ctx.writeAndFlush(message1);

        CM_Login req2 = CM_Login.valueOf("jack", "jack123");
        byte[] bytes2 = ObjectByteUtil.objectToByteArray(req2);
        MessageContent message2 = new MessageContent(bytes2.length, req2);
        ctx.writeAndFlush(message2);
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.println("输入客户端请求，指令如下：" + "\r\n"
//                    + "1、注册账号： reg 账号id 账号名称 账号密码（举例： reg yxm 小明 pwd123）" + "\r\n"
//                    + "2、登陆账号：  login 账号id 账号密码 （举例： login yxm pwd123）" + "\r\n"
//                    + "3、切换地图： changemap 账号id 地图id（目前只有三张地图1001-1003 举例： changemap yxm 1002）" + "\r\n"
//                    + "4、打印账号所在地图信息： printMapInfo （举例： printMapInfo yxm）" + "\r\n"
//                    + "5、移动坐标： move 账号id x坐标 y坐标（举例： move yxm 5 5）" + "\r\n"
//                    + "6、打印账号信息：printAccInfo 账号id（举例： printAccInfo yxm ）"
//            );
//            String req = scanner.nextLine();
//            CM_GMcommand cm=CM_GMcommand.vauleOf(req);
//            byte[] content = ObjectByteUtil.objectToByteArray(cm);
//            int length = content.length;
//            MessageContent messageContent = new MessageContent(length, cm);
//            ctx.writeAndFlush(messageContent);
//        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {

            MessageContent messageContent = (MessageContent) msg;
            String str = (String) messageContent.getContent();
            System.out.println(str);


//            ByteBuf bb = (ByteBuf) msg;
//            byte[] respByte = new byte[bb.readableBytes()];
//            bb.readBytes(respByte);
//            String respStr = new String(respByte, Charset.forName("UTF-8"));
//            System.out.println("客户端--收到响应：" + respStr);

            // 直接转成对象
//          handlerObject(ctx, msg);

        } finally {
            // 必须释放msg数据
            ReferenceCountUtil.release(msg);

        }
    }

    private void handlerObject(ChannelHandlerContext ctx, Object msg) {


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("客户端读取数据完毕");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        System.out.println("client 读取数据出现异常");
        cause.printStackTrace();
        ctx.close();
    }
}
