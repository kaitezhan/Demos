package com.boneix.base.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * 返回值base64加密 重写writeInternal方法实现base64加密
 *
 * @author bone
 * @version [1.0, 2016年1月10日]
 */
public class FastJsonMessageConverter extends FastJsonHttpMessageConverter {

    private SerializerFeature[] features = new SerializerFeature[0];

    private Charset charset = UTF8;

    private String encoding = "UTF-8";

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        // 返回信息
        String message = JSON.toJSONString(obj, features);
        // base64处理
        String text = new String(
                Base64.encodeBase64(message.getBytes(encoding)), encoding);
        byte[] bytes = text.getBytes(charset);
        out.write(bytes);
    }
}
