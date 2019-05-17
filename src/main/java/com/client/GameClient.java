package com.client;


import com.SpringContext;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.net.ssl.SSLException;

/**
 * @author yuxianming
 * @date 2019/4/24 12:01
 */
public class GameClient {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = "localhost";
    static final int PORT = 8888;
    static final int SIZE = 256;
    private static final String APPLICATION_CONTEXT = "applicationContext.xml";
    private static ClassPathXmlApplicationContext applicationContext;

    public static void main(String[] args) throws InterruptedException, SSLException {


        applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT);
        applicationContext.start();
        final SslContext sslContext;
        SpringContext.getGlobalService().onStart();
        if (SSL) {
            sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslContext = null;
        }
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ClientChannelHandler());

            // 开启客户端
            ChannelFuture cf = b.connect(HOST, PORT).sync();

            //等待直到连接中断
            cf.channel().closeFuture().sync();
//            while (true) {
//                System.out.println("输入客户端请求：");
//                Scanner scanner = new Scanner(System.in);
//                String req = scanner.nextLine();
//                cf.channel().writeAndFlush(Unpooled.copiedBuffer(req.getBytes(Charset.forName("UTF-8"))));
//
//
//            }

        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }
}
