package com.boneix.netty.idlestate;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.util.Date;

/**
 * Created by rzhang on 2017/3/7.
 */
public class IdelClientOutHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("IdelClientOutHandler read "+new Date());
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("IdelClientOutHandler write "+new Date());
        super.write(ctx, msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        System.out.println("IdelClientOutHandler flush "+new Date());
        super.flush(ctx);
    }
}
