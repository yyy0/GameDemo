package com.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author yuxianming
 * @date 2019/4/24 12:01
 */
public class EchoClient {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = "localhost";
    static final int PORT = 8888;
    static final int SIZE = 256;

    public static void main(String[] args) throws InterruptedException, SSLException {


        final SslContext sslContext;

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
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslContext != null) {
                                p.addLast(sslContext.newHandler(ch.alloc(), HOST, PORT));
                            }
                            //p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(new EchoClientHandler());
                        }
                    });

            // 开启客户端
            ChannelFuture cf = b.connect(HOST, PORT).sync();

            while (true) {
                System.out.println("输入客户端请求：");
                Scanner scanner = new Scanner(System.in);
                String req = scanner.next();
                cf.channel().writeAndFlush(Unpooled.copiedBuffer(req.getBytes(Charset.forName("UTF-8"))));
                // cf.channel().writeAndFlush(Unpooled.copiedBuffer("Test2 yxm".getBytes()));
                // 等待直到客户端关闭
                //
            }


        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }
}
