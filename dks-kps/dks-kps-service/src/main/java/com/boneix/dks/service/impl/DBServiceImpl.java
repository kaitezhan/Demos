package com.boneix.dks.service.impl;

import com.boneix.dks.dao.InitInfoDao;
import com.boneix.dks.dao.SysInfoDao;
import com.boneix.dks.domain.InitInfo;
import com.boneix.dks.domain.SysInfo;
import com.boneix.dks.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public long insertInitInfo(int sysId, long newValue) {
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
    public long deleteInitInfo(int sysId) {
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
    public long updateInitInfo(int sysId, long newValue) {
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
    public long queryCurrentValue(int sysId) {
        return initInfoDao.queryCurrentValue(sysId);
    }

    @Override
    public long insertSysInfo(String sysName) {
        int sysId = sysInfoDao.insertByName(sysName);
        InitInfo initInfo = new InitInfo();
        initInfo.setSystemName(sysName);
        initInfo.setSysId(sysId);
        long hits = initInfoDao.insert(initInfo);

        return sysId;
    }

    @Override
    public long deleteSysInfo(int sysId) {
        return sysInfoDao.deleteById(sysId);
    }

    @Override
    public long updateSysInfo(int sysId, String sysName) {
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
}
