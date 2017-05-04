package com.boneix.netty.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by rzhang on 2017/3/3.
 */
public class CustomClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CustomMsg customMsg=new CustomMsg((byte)0xAB, (byte)0xCD, "Hello,Server".length(), "Hello,Server");
        ctx.writeAndFlush(customMsg);
    }
}
