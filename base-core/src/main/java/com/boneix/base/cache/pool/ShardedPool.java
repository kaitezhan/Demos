/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2014-11-20
 * Description:CJedisPool.java 
 */
package com.boneix.base.cache.pool;

import com.boneix.base.cache.util.CacheClientUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author wangchong
 */
public class ShardedPool extends ShardedJedisPool {

    private JedisPoolConfig poolConfig;

    private List<HostPair> hostPairs;

    public ShardedPool(String host, int port) {
        super(new JedisPoolConfig(), Arrays.asList(new JedisShardInfo(host, port)));
        hostPairs = new ArrayList<>();
        poolConfig = new JedisPoolConfig();
        hostPairs.add(new HostPair(host, port));
    }

    public ShardedPool(JedisPoolConfig poolConfig, List<JedisShardInfo> shards) {
        super(poolConfig, shards);
        this.poolConfig = poolConfig;
        this.hostPairs = CacheClientUtils.convertHostPair(shards);
    }

    private ShardedPool(JedisPoolConfig poolConfig, List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
        super(poolConfig, shards, algo, keyTagPattern);
        this.poolConfig = poolConfig;
        this.hostPairs = CacheClientUtils.convertHostPair(shards);
    }

    private ShardedPool(JedisPoolConfig poolConfig, List<JedisShardInfo> shards, Hashing algo) {
        super(poolConfig, shards, algo);
        this.poolConfig = poolConfig;
        this.hostPairs = CacheClientUtils.convertHostPair(shards);
    }

    private ShardedPool(JedisPoolConfig poolConfig, List<JedisShardInfo> shards, Pattern keyTagPattern) {
        super(poolConfig, shards, keyTagPattern);
        this.poolConfig = poolConfig;
        this.hostPairs = CacheClientUtils.convertHostPair(shards);
    }

    public JedisPoolConfig getPoolConfig() {
        return poolConfig;
    }

    public List<HostPair> getHostPairs() {
        return hostPairs;
    }
}
