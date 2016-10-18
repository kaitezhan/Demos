package com.boneix.base.client;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

class RestCodec {
    static String decodeData(String base64Data) throws RestException {
        try {
            return new String(Base64.decodeBase64(base64Data.getBytes("utf-8")), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RestException(e.getMessage(), e.getCause());
        }
    }

    static String encodeData(String binaryData) throws RestException {
        try {
            if (null == binaryData) {
                return null;
            }

            return Base64.encodeBase64String(binaryData.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RestException(e.getMessage(), e.getCause());
        }
    }
}