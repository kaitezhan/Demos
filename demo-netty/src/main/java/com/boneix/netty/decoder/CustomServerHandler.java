package com.boneix.netty.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by rzhang on 2017/3/3.
 */
public class CustomServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        if (o instanceof CustomMsg) {
            CustomMsg customMsg = (CustomMsg) o;
            System.out.println("Client->Server : " + ctx.channel().remoteAddress() + " send " + customMsg.getBody());

        }
    }
}
