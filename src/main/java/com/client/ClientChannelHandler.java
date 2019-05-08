package com.client;

import com.yxm.server.message.MessageDecoder;
import com.yxm.server.message.MessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author yuxianming
 * @date 2019/5/6 23:58
 */
public class ClientChannelHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new MessageEncoder());
        ch.pipeline().addLast(new MessageDecoder());
        ch.pipeline().addLast(new GameClientHandler());
    }
}
