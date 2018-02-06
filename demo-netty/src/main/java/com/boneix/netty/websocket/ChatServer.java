package com.boneix.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * Created by rzhang on 2018/2/6.
 */
public class ChatServer {

    private final ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Channel channel;


    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(group));
        ChannelFuture future = bootstrap.bind(address).syncUninterruptibly();
        channel = future.channel();
        return future;
    }

    private ChannelHandler createInitializer(ChannelGroup group) {
        return new ChatServerInitializer(group);
    }

    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        group.close();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        final ChatServer server = new ChatServer();
        ChannelFuture future = server.start(new InetSocketAddress(2048));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.destroy()));
        future.channel().closeFuture().syncUninterruptibly();


    }


}
