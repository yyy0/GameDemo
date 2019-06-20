package com.client;


import com.SpringContext;
import com.server.log.LoggerUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.net.ssl.SSLException;

/**
 * @author yuxianming
 * @date 2019/4/24 12:01
 */
public class GameClient {

    static final String HOST = "10.9.13.52";
    static final int PORT = 8888;
    private static final String APPLICATION_CONTEXT = "applicationContext.xml";
    private static ClassPathXmlApplicationContext applicationContext;

    public static void main(String[] args) throws InterruptedException, SSLException {


        LoggerUtil.info("客户端开启");
        applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT);
        applicationContext.start();
        LoggerUtil.info("加载resource资源");
        SpringContext.getResourceManager().loadNewResource();
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

        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }
}
