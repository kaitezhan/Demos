package com.boneix.netty.tsf;

/**
 * Created by rzhang on 2017/3/6.
 */
public class CommonMsg {

    //类型  请求方系统编号 0xAB 表示A系统，0xBC 表示B系统
    private byte caller;

    //类型  调用方系统编号 0xAB 表示A系统，0xBC 表示B系统
    private byte replier;

    //信息标志  0xAB 表示心跳包 0xBC 表示超时包  0x00 业务信息包
    private byte flag;

    //接口名称
    private char api;

    //主题信息的长度
    private int length;

    //主题信息
    private String body;

    public CommonMsg() {
    }

    public CommonMsg(byte caller, byte replier, byte flag, char api, int length, String body) {
        this.caller = caller;
        this.replier = replier;
        this.flag = flag;
        this.api = api;
        this.length = length;
        this.body = body;
    }

    public byte getCaller() {
        return caller;
    }

    public void setCaller(byte caller) {
        this.caller = caller;
    }

    public byte getReplier() {
        return replier;
    }

    public void setReplier(byte replier) {
        this.replier = replier;
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public char getApi() {
        return api;
    }

    public void setApi(char api) {
        this.api = api;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
