/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2014-11-20
 * Description:Credis.java 
 */
package com.boneix.base.cache.pool;

import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangchong
 */
public class Credis {
    private static final long EXPIRE_THRESHOLD = 5000;

    private ShardedJedis shardedJedis;

    private volatile ConcurrentHashMap<String, String> hostPairs = new ConcurrentHashMap<String, String>();

    private volatile long expires;

    private volatile int dbIndex;

    private int slowThreshold;

    @Override
    public String toString() {
        return "Credis [hostPairs=" + hostPairs + ", expires=" + expires + "]";
    }

    public Credis(ShardedJedis shardedJedis) {
        super();
        this.shardedJedis = shardedJedis;
        getHostPairs();
    }

    public ShardedJedis getShardedJedis() {
        return shardedJedis;
    }

    public Jedis getJedis(String key) {
        return shardedJedis.getShard(key);
    }

    public Map<String, String> getHostPairs() {
        if (CollectionUtils.isEmpty(hostPairs) || shardedJedis.getAllShards().size() != hostPairs.size()) {
            for (Jedis jedis : shardedJedis.getAllShards()) {
                if (!jedis.getClient().isConnected()) {
                    jedis.getClient().connect();
                }
                InetAddress inetAddress = jedis.getClient().getSocket().getInetAddress();
                hostPairs.put(jedis.getClient().getHost(), inetAddress.getHostAddress());
            }
        }
        return hostPairs;
    }

    public void select(int dbIndex) {
        for (Jedis jedis : shardedJedis.getAllShards()) {
            if (jedis.getClient().getDB() != dbIndex) {
                jedis.select(dbIndex);
            }
        }
        this.dbIndex = dbIndex;
    }

    public Set<String> keys(String key) {
        Set<String> strings = new HashSet<String>();
        for (Jedis jedis : shardedJedis.getAllShards()) {
            strings.addAll(jedis.keys(key));
        }
        return strings;
    }

    public String flushDB() {
        StringBuffer buffer = new StringBuffer();
        for (Jedis jedis : shardedJedis.getAllShards()) {
            buffer.append(jedis.flushDB()).append(",");
        }
        return buffer.toString();
    }

    public int getSlowThreshold() {
        return slowThreshold;
    }

    public void setSlowThreshold(int slowThreshold) {
        this.slowThreshold = slowThreshold;
    }

    public boolean validate() {
        return System.currentTimeMillis() - expires > EXPIRE_THRESHOLD;
    }

    public int getDbIndex() {
        return dbIndex;
    }

    public long getExpires() {
        return expires;
    }

    public void update() {
        expires = System.currentTimeMillis();
    }
}
