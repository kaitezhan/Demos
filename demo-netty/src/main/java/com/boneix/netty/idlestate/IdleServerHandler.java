package com.boneix.netty.idlestate;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

/**
 * Created by rzhang on 2017/3/6.
 */
public class IdleServerHandler extends ChannelInboundHandlerAdapter {

    private int loss_connect_time = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("IdleServerHandler channelRead" + new Date());
        System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
        ctx.write(msg);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE == event.state()) {
                loss_connect_time++;
                System.out.println("丢失客户端链接 "+ new Date());
                if (loss_connect_time > 2) {
                    System.out.println("丢失客户端链接,即将关闭channel "+ new Date());
                    ctx.channel().close();
                }
            } else {
                super.userEventTriggered(ctx, evt);
            }

        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("IdleServerHandler channelRegistered ");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("IdleServerHandler channelUnregistered ");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("IdleServerHandler channelActive ");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("IdleServerHandler channelInactive ");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("IdleServerHandler channelReadComplete " + new Date());
    }


}
