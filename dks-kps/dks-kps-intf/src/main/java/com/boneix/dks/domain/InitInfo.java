package com.boneix.dks.domain;

import com.boneix.base.domain.BaseEntity;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
public class InitInfo extends BaseEntity {
    private static final long serialVersionUID = 1046974878554218859L;

    private long currentValue;

    private int sysId;

    private String systemName;

    public InitInfo() {
    }

    public InitInfo(long currentValue) {
        this.currentValue = currentValue;
    }

    public long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(long currentValue) {
        this.currentValue = currentValue;
    }


    public int getSysId() {
        return sysId;
    }

    public void setSysId(int sysId) {
        this.sysId = sysId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
