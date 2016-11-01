package com.boneix.dks.domain;

/**
 * Created by zhangrong5 on 2016/10/31.
 */
public class SystemsInfoVo extends SysInfo {

    private static final long serialVersionUID = -1333099028667371199L;

    private long currentValue;

    private long usedValue;


    public SystemsInfoVo() {
    }

    public long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(long currentValue) {
        this.currentValue = currentValue;
    }

    public long getUsedValue() {
        return usedValue;
    }

    public void setUsedValue(long usedValue) {
        this.usedValue = usedValue;
    }

    public static SystemsInfoVo superClone(SysInfo sysInfo) {
        SystemsInfoVo systemsInfoVo = new SystemsInfoVo();
        systemsInfoVo.setId(sysInfo.getId());
        systemsInfoVo.setSystemName(sysInfo.getSystemName());
        systemsInfoVo.setCreateTime(sysInfo.getCreateTime());
        systemsInfoVo.setDeleteFlag(sysInfo.getDeleteFlag());
        systemsInfoVo.setAuthorityCode(sysInfo.getAuthorityCode());
        return systemsInfoVo;
    }
}
