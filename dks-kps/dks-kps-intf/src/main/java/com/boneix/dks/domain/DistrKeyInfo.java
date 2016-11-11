package com.boneix.dks.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangrong5 on 2016/11/10.
 */
public class DistrKeyInfo implements Serializable {

    private static final long serialVersionUID = 4245892057604090407L;

    private String key;

    private SysInfo sysInfo;

    private Date createTime;

    private int invalidTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(int invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SysInfo getSysInfo() {
        return sysInfo;
    }

    public void setSysInfo(SysInfo sysInfo) {
        this.sysInfo = sysInfo;
    }
}
