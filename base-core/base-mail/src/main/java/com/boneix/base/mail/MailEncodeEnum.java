package com.boneix.base.mail;

/**
 * Created by rzhang on 2017/7/3.
 */
public enum MailEncodeEnum {
    GBK("GBK"),
    UTF8("UTF-8");
    private String value;

    MailEncodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
