package com.boneix.demo.domain;



/**
 * Created by zhangrong5 on 2016/10/27.
 */
public class SysInfo extends BaseEntity {

    private static final long serialVersionUID = 2631320379431915866L;

    private String systemName;

    private String authorityCode;

   // private String machineName;

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

//    public String getMachineName() {
//        return machineName;
//    }
//
//    public void setMachineName(String machineName) {
//        this.machineName = machineName;
//    }
}
