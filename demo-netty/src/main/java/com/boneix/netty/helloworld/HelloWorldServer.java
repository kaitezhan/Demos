package com.boneix.netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * Created by Home on 2017/2/6.
 */
public class HelloWorldServer {
    private  int port;

    public HelloWorldServer(int port) {
        this.port = port;
    }

    public  void start(){
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap sbs=new ServerBootstrap().group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port)).childHandler(new ChannelInitializer<SocketChannel>(){

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast("decoder",new StringDecoder());
                    socketChannel.pipeline().addLast("encoder",new StringEncoder());
                    socketChannel.pipeline().addLast(new HelloWorldServerHandler());
                }
            }).option(ChannelOption.SO_BACKLOG,128).childOption(ChannelOption.SO_KEEPALIVE,true);
            //绑定端口，开始接收进来的连接
            ChannelFuture future=sbs.bind(port).sync();
            System.out.println("Server start listen at "+port);
            future.channel().closeFuture().sync();
        }catch (Exception e){
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public  static  void main(String[] args) throws  Exception{
        int port;
        if(args.length>0){
            port=Integer.valueOf(args[0]);
        }else{
            port=8080;
        }
        new HelloWorldServer(port).start();
    }
}
