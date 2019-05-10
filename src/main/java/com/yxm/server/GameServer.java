package com.yxm.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

/**
 * @author yuxianming
 * @date 2019/4/24 15:52
 */
public class GameServer {


    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = 8888;
    private static Logger logger = LoggerFactory.getLogger(GameServer.class);


    public void connect() {
        {
            // Configure SSL.
            final SslContext sslCtx;
            if (SSL) {
                SelfSignedCertificate ssc = null;
                try {
                    ssc = new SelfSignedCertificate();
                    sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (SSLException e) {
                    e.printStackTrace();
                }
            } else {
                sslCtx = null;
            }

            //因为bossGroup仅接收客户端连接，不做复杂的逻辑处理，为了尽可能减少资源的占用，取值越小越好
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            //workerGroup作为worker，处理boss接收的连接的流量和将接收的连接注册进入这个worker
            EventLoopGroup workerGroup = new NioEventLoopGroup();


            long start = System.nanoTime();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        //指定使用NioServerSocketChannel产生一个Channel用来接收连接
                        .channel(NioServerSocketChannel.class)
                        //BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
                        //Option是为了NioServerSocketChannel设置的，用来接收传入连接的
                        .option(ChannelOption.SO_BACKLOG, 1024)
                        .childOption(ChannelOption.SO_KEEPALIVE, true)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new MyChannelHandler());
                // Start the server.
                long end = System.nanoTime() - start;
                logger.info("服务器启动，耗时{}s", TimeUnit.NANOSECONDS.toSeconds(end));
                // 绑定端口
                ChannelFuture f = null;
                f = b.bind(PORT).sync();
                f.channel().closeFuture().sync();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Shut down all event loops to terminate all threads.
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }
    }

}
