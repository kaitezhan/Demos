package com.boneix.netty.websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Objects;

/**
 * Created by rzhang on 2018/2/5.
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        //如果是 websocket 请求，请求地址 uri 等于 wsuri      
        if (wsUri.equalsIgnoreCase(msg.getUri())) {
            //将消息转发到下一个 ChannelHandler
            ctx.fireChannelRead(msg.retain());
        } else {
            //如果不是 websocket 请求
            if (HttpHeaders.is100ContinueExpected(msg)) {
                //如果 HTTP 请求头部包含 Expect:  100-­‐continue 则响应请求
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
                ctx.writeAndFlush(response);
            }
            //获取 index.html 的内容响应给客户端
            String filePath = "/index.html";
            RandomAccessFile file = null;
            try {
                URL url = this.getClass().getResource(filePath);
                File urlFile = new File(url.toURI());
                file = new RandomAccessFile(urlFile, "r");
                HttpResponse response = new DefaultHttpResponse(msg.getProtocolVersion(), HttpResponseStatus.OK);
                response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html;charset=UTF-8");
                boolean keepAlive = HttpHeaders.isKeepAlive(msg);
                //如果 http 请求保持活跃，设置 http 请求头部信息
                //并响应请求  
                if (keepAlive) {
                    response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
                    response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                }
                ctx.write(response);
                //如果不是 https 请求，将 index.html 内容写入通道  
                if (ctx.pipeline().get(SslHandler.class) == null) {
                    ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
                } else {
                    ctx.write(new ChunkedNioFile(file.getChannel()));
                }
                //标识响应内容结束并刷新通道  
                ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
                if (!keepAlive) {
                    //如果 http 请求不活跃，关闭 http 连接      
                    future.addListener(ChannelFutureListener.CLOSE);
                }

            } finally {
                if (Objects.nonNull(file)) {
                    file.close();
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
