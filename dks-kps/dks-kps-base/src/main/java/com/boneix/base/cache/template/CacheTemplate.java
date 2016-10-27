/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2014-8-23
 * Description:CacleTemplate.java 
 */
package com.boneix.base.cache.template;

import com.boneix.base.cache.jmx.CacheMonitor;
import com.boneix.base.cache.pool.Credis;
import com.boneix.base.cache.util.CacheClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author wangchong
 */
public class CacheTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheTemplate.class);

    private CacheMonitor monitor;

    private int dbIndex;

    private boolean isThrow;

    public CacheTemplate(CacheMonitor monitor, int dbIndex) {
        super();
        this.monitor = monitor;
        this.dbIndex = dbIndex;
    }

    public CacheTemplate(CacheMonitor monitor, int dbIndex, boolean isThrow) {
        super();
        this.monitor = monitor;
        this.dbIndex = dbIndex;
        this.isThrow = isThrow;
    }

    public <T> T execute(CacheCallback<T> callback) {
        Credis credis = null;
        if (monitor.isUsed()) {
            long start = System.currentTimeMillis();
            int slowThreshold = 0;
            try {
                credis = monitor.getResource();
                slowThreshold = credis.getSlowThreshold();
                if (this.dbIndex > 0 && this.dbIndex != credis.getDbIndex()) {
                    credis.select(dbIndex);
                }
                return callback.doInAction(credis);
            } catch (JedisConnectionException e) {
                CacheClientUtils.broken(monitor, credis);
                if (isThrow) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                if (isThrow) {
                    throw new RuntimeException(e);
                }
            } finally {
                CacheClientUtils.release(monitor, credis);
                long end = System.currentTimeMillis();
                if (slowThreshold < end - start) {
                    LOGGER.info("{} execute time : {} ms", callback.getIdentity(), end - start);
                }
            }
        }
        return null;
    }

}
