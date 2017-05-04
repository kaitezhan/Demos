package com.boneix.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by rzhang on 2017/3/3.
 */
public class CustomDecoder extends LengthFieldBasedFrameDecoder {

    //判断传送客户端传送过来的数据是否按照协议传输，头部信息的大小应该是 byte+byte+int = 1+1+4 = 6
    private static final int HEADER_SIZE = 6;

    private byte type;

    private byte flag;

    private int length;

    private String body;


    /**
     * maxFrameLength：解码的帧的最大长度
     * lengthFieldOffset ：长度属性的起始位（偏移位），包中存放有整个大数据包长度的字节，这段字节的其实位置
     * lengthFieldLength：长度属性的长度，即存放整个大数据包长度的字节所占的长度
     * lengthAdjustmen：长度调节值，在总长被定义为包含包头长度时，修正信息长度。
     * initialBytesToStrip：跳过的字节数，根据需要我们跳过lengthFieldLength个字节，以便接收端直接接受到不含“长度属性”的内容
     * failFast ：为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
     */
    public CustomDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in == null) {
            return null;
        }
        if (in.readableBytes() < HEADER_SIZE) {
            throw new Exception("less than HEADER_SIZE");
        }
        //注意在读的过程中，readIndex的指针也在移动
        type = in.readByte();

        flag = in.readByte();

        length = in.readInt();

        if (in.readableBytes() < length) {
            throw new Exception("body's length unmatch");
        }

        ByteBuf buf = in.readBytes(length);
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        body = new String(req, "UTF-8");

        CustomMsg customMsg = new CustomMsg(type, flag, length, body);
        return customMsg;
    }
}
