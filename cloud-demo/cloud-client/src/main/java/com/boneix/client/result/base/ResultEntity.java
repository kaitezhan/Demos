package com.boneix.client.result.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 返回类型类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultEntity implements Serializable {

    private static final long serialVersionUID = 5366907122763659782L;

    private long code;
    private String message;
    private Object data;

    public ResultEntity() {
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}