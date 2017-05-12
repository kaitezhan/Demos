package com.boneix.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密工具类
 *
 * @author Boneix
 * @version [1.0, 2016年1月18日]
 */
public class EncryptUtils {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptUtils.class);

    /**
     * SHA1加密
     *
     * @param decript 需要加密的内容
     * @return String
     */
    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            LOG.error("EncryptUtils.SHA1 NoSuchAlgorithmException error {}", e);
        }
        return "";
    }

    /**
     * SHA加密
     *
     * @param decript 需要加密的内容
     * @return String
     */
    public static String SHA(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            LOG.error("EncryptUtils.SHA NoSuchAlgorithmException error {}", e);
        }
        return "";
    }

    /**
     * MD5加密
     *
     * @param input 需要加密的内容
     * @return String
     */
    public static String MD5(String input) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(input.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < md.length; i++) {
                String shaHex = Integer.toHexString(md[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LOG.error("EncryptUtils.MD5 NoSuchAlgorithmException error {}", e);
        }
        return "";
    }

    /**
     * 加密encryptAES
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return byte
     */
    public static byte[] encryptAES(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            LOG.error("EncryptUtils.encryptAES NoSuchAlgorithmException error {}", e);
        } catch (NoSuchPaddingException e) {
            LOG.error("EncryptUtils.encryptAES NoSuchPaddingException error {}", e);
        } catch (InvalidKeyException e) {
            LOG.error("EncryptUtils.encryptAES InvalidKeyException error {}", e);
        } catch (UnsupportedEncodingException e) {
            LOG.error("EncryptUtils.encryptAES UnsupportedEncodingException error {}", e);
        } catch (IllegalBlockSizeException e) {
            LOG.error("EncryptUtils.encryptAES IllegalBlockSizeException error {}", e);
        } catch (BadPaddingException e) {
            LOG.error("EncryptUtils.encryptAES BadPaddingException error {}", e);
        }
        return null;
    }

    /**
     * 解密decryptAES
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return byte
     */
    public static byte[] decryptAES(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            LOG.error("EncryptUtils.decryptAES NoSuchAlgorithmException error {}", e);
        } catch (NoSuchPaddingException e) {
            LOG.error("EncryptUtils.decryptAES NoSuchPaddingException error {}", e);
        } catch (InvalidKeyException e) {
            LOG.error("EncryptUtils.decryptAES InvalidKeyException error {}", e);
        } catch (IllegalBlockSizeException e) {
            LOG.error("EncryptUtils.decryptAES IllegalBlockSizeException error {}", e);
        } catch (BadPaddingException e) {
            LOG.error("EncryptUtils.decryptAES BadPaddingException error {}", e);
        }
        return null;
    }

}
