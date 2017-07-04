package com.boneix.base.utils.common;

import java.util.regex.Pattern;

/**
 * 参数验证工具类
 *
 * @Authro qygu.
 * @Email qiyao.gu@qq.com.
 * @Date 2017/3/20.
 */
public final class ValidationUtils {

    private static final String MOBILE_REGEX = "^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D])|(17[0-9]))\\d{8}";

    private ValidationUtils() {
    }


    /**
     * 手机号的验证
     */
    public static boolean validateMobile(String mobile) {
        return Pattern.compile(MOBILE_REGEX).matcher(mobile).matches();
    }

}