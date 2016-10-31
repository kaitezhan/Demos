package com.boneix.dks.service.impl;

import com.boneix.dks.dao.InitInfoDao;
import com.boneix.dks.dao.SysInfoDao;
import com.boneix.dks.domain.InitInfo;
import com.boneix.dks.domain.SysInfo;
import com.boneix.dks.domain.SystemsInfoVo;
import com.boneix.dks.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangrong5 on 2016/10/27.
 */
@Service
public class DBServiceImpl implements DBService {

    private static final Logger LOG = LoggerFactory.getLogger(DBServiceImpl.class);

    @Resource
    private InitInfoDao initInfoDao;

    @Resource
    private SysInfoDao sysInfoDao;

    @Override
    public long insertInitInfo(long sysId, long newValue) {
        SysInfo sysInfo = sysInfoDao.selectById(sysId);
        long hits = 0;
        if (sysInfo != null) {
            InitInfo initInfo = new InitInfo();
            initInfo.setSysId(sysId);
            initInfo.setSystemName(sysInfo.getSystemName());
            initInfo.setCurrentValue(newValue);

            hits = initInfoDao.insert(initInfo);
        } else {
            LOG.info("未查询到此系统id:{}", sysId);
        }
        return hits;
    }

    @Override
    public long deleteInitInfo(long sysId) {
        int tmpNum = initInfoDao.querySystemCount(sysId);
        int hits = 0;
        if (tmpNum > 0) {
            hits = initInfoDao.deleteCurrentValue(sysId);
        } else {
            LOG.info("未查询到此系统id:{}", sysId);
        }
        return hits;
    }

    @Override
    public long updateInitInfo(long sysId, long newValue) {
        int tmpNum = initInfoDao.querySystemCount(sysId);
        int hits = 0;
        if (tmpNum > 0) {
            hits = initInfoDao.updateCurrentValue(sysId, newValue);
        } else {
            LOG.info("未查询到此系统id:{}", sysId);
        }
        return hits;
    }

    @Override
    public long queryCurrentValue(long sysId) {
        return initInfoDao.queryCurrentValue(sysId);
    }

    @Override
    public long insertSysInfo(SysInfo sysInfo) {
        sysInfoDao.insertByName(sysInfo);
        InitInfo initInfo = new InitInfo();
        initInfo.setSystemName(sysInfo.getSystemName());
        initInfo.setSysId(sysInfo.getId());
        initInfoDao.insert(initInfo);

        return sysInfo.getId();
    }

    @Override
    public long deleteSysInfo(long sysId) {
        return sysInfoDao.deleteById(sysId);
    }

    @Override
    public long updateSysInfo(long sysId, String sysName) {
        long tmpNum = sysInfoDao.querySystemCount(sysId);
        long hits = 0;
        if (tmpNum > 0) {
            SysInfo sysInfo = new SysInfo();
            sysInfo.setId(sysId);
            sysInfo.setSystemName(sysName);
            hits = sysInfoDao.updateById(sysInfo);
        } else {
            LOG.info("未查询到此系统id:{}", sysId);
        }
        return hits;
    }

    @Override
    public List<SystemsInfoVo> selectSystemsInfo() {
        List<SystemsInfoVo> SystemsInfoList = new ArrayList<>();
        List<SysInfo> sysList = sysInfoDao.selectAll();
        for (SysInfo sysInfo : sysList) {
            SystemsInfoVo systemsInfoVo = SystemsInfoVo.superClone(sysInfo);
            systemsInfoVo.setCurrentValue(initInfoDao.queryCurrentValue(sysInfo.getId()));
            SystemsInfoList.add(systemsInfoVo);
        }

        return SystemsInfoList;
    }
}
