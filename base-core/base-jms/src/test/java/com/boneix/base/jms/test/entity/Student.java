package com.boneix.base.jms.test.entity;

import java.io.Serializable;

/**
 * Created by rzhang on 2017/7/4.
 */
public class Student implements Serializable {
    private static final long serialVersionUID = -4524379981212372643L;

    private Integer stdId;

    private String stdName;

    public Student(Integer stdId, String stdName) {
        this.stdId = stdId;
        this.stdName = stdName;
    }

    public Integer getStdId() {
        return stdId;
    }

    public void setStdId(Integer stdId) {
        this.stdId = stdId;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }
}
