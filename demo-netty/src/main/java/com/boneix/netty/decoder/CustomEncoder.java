package com.boneix.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by rzhang on 2017/3/3.
 */
public class CustomEncoder extends MessageToByteEncoder<CustomMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CustomMsg customMsg, ByteBuf byteBuf) throws Exception {
        if (null == customMsg) {
            throw new Exception("msg is null");
        }
        String body = customMsg.getBody();
        byte[] bodyByte = body.getBytes();
        byteBuf.writeByte(customMsg.getType());
        byteBuf.writeByte(customMsg.getFlag());
        byteBuf.writeInt(customMsg.getLength());
        byteBuf.writeBytes(bodyByte);
    }
}
