package com.boneix.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by rzhang on 2017/3/3.
 */
public class BaseClientHandler extends ChannelInboundHandlerAdapter {
    private byte[] req;

    private int counter;

    public BaseClientHandler() {
//        String longMsg = "　From the user’s point of view，a TCP/IP internet[2] appears to be a set of application programs that use the network to carry out useful communication tasks．We use the term interoperability to refer to the ability of diverse computing systems to cooperate in solving computational problems．Internet application programs exhibit a high degree of interoperability．Most users that access the Internet do so merely by running application programs without understanding the TCP/IP technology，the structure of the underlying internet，or even the path the data travels to its destination；they rely on the application programs and the underlying network software to handle such details．Only programmers who write network application programs need to view the internet as a network and need to understand some of the technology．";
//        req = longMsg.getBytes();
        req = "Boneix is learner".getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;

        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }

        //
//        message = Unpooled.buffer(req.length);
//        message.writeBytes(req);
//        ctx.writeAndFlush(message);
//        message = Unpooled.buffer(req.length);
//        message.writeBytes(req);
//        ctx.writeAndFlush(message);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String buf = (String) msg;
        System.out.println("Now is : " + buf + " ; the counter is : " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
