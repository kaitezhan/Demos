package com.boneix.dks.domain;

import com.boneix.base.domain.BaseEntity;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
public class SysInfo extends BaseEntity {

    private static final long serialVersionUID = 2631320379431915866L;

    private String systemName;

    private String authorityCode;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }
}
