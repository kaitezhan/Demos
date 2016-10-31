package com.boneix.dks.domain;

/**
 * Created by zhangrong5 on 2016/10/31.
 */
public class SystemsInfoVo extends SysInfo {

    private static final long serialVersionUID = -1333099028667371199L;

    private long currentValue;

    public SystemsInfoVo() {
    }

    public long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(long currentValue) {
        this.currentValue = currentValue;
    }

    public static SystemsInfoVo superClone(SysInfo sysInfo) {
        SystemsInfoVo systemsInfoVo = new SystemsInfoVo();
        systemsInfoVo.setId(sysInfo.getId());
        systemsInfoVo.setSystemName(sysInfo.getSystemName());
        systemsInfoVo.setCreateTime(sysInfo.getCreateTime());
        systemsInfoVo.setDeleteFlag(sysInfo.getDeleteFlag());
        return systemsInfoVo;
    }
}
