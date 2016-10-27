/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2014-11-18
 * Description:CacheMonitor.java 
 */
package com.boneix.base.cache.jmx;

import com.boneix.base.annotation.JmxAttribute;
import com.boneix.base.annotation.JmxOperation;
import com.boneix.base.annotation.JmxSource;
import com.boneix.base.cache.pool.Credis;
import com.boneix.base.cache.pool.DnsChecker;
import com.boneix.base.cache.pool.HostPair;
import com.boneix.base.cache.pool.ShardedPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 缓存监控
 *
 * @author wangchong
 */
@JmxSource(objectName = "com.boneix.cacheMonitor:type=jeidsCount")
public class CacheMonitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheMonitor.class);

    private ShardedPool shardedPool;

    private Map<ShardedJedis, Credis> jedisCache = new ConcurrentHashMap<ShardedJedis, Credis>();

    @JmxAttribute
    private AtomicInteger usingCount = new AtomicInteger(0);

    @JmxAttribute
    private AtomicInteger errorCount = new AtomicInteger(0);

    @JmxAttribute
    private boolean isUsed = true;

    @JmxAttribute
    // 默认10分钟
    private int threshold = 600 * 1000;

    @JmxAttribute
    // 慢查2s
    private int slowThreshold = 2 * 1000;

    private JedisPoolConfig poolConfig;

    private volatile List<HostPair> hostPairs;

    private DnsChecker dnsChecker;

    private ScheduledExecutorService executorService;

    public CacheMonitor(ShardedPool shardedPool) {
        this.shardedPool = shardedPool;
        this.poolConfig = shardedPool.getPoolConfig();
        this.hostPairs = shardedPool.getHostPairs();
        initialize0();
    }

    public CacheMonitor(JedisPoolConfig poolConfig, List<HostPair> hostPairs) {
        super();
        this.poolConfig = poolConfig;
        this.hostPairs = hostPairs;
        initialize();
    }

    private void expireClean() {
        long currentTime = System.currentTimeMillis();
        Iterator<Entry<ShardedJedis, Credis>> it = jedisCache.entrySet().iterator();
        while (it.hasNext()) {
            Entry<ShardedJedis, Credis> entry = it.next();
            // 过期
            if (currentTime - entry.getValue().getExpires() > threshold) {

                LOGGER.debug("CacheMonitor.expireClean - {}", entry.getValue());
                // 清理
                it.remove();
            }

        }
    }

    public synchronized void initialize() {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(hostPairs.size());
        for (HostPair hostPair : hostPairs) {
            shards.add(new JedisShardInfo(hostPair.getHost(), hostPair.getPort(), hostPair.getTimeout()));
        }
        this.shardedPool = new ShardedPool(poolConfig, shards);
        initialize0();
    }

    public synchronized void initialize0() {
        executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(CacheMonitor.class.getName() + "-Cleaner");
                thread.setDaemon(true);
                return thread;
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (executorService != null) {
                    try {
                        executorService.shutdown();
                        executorService.awaitTermination(5, TimeUnit.MINUTES);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });

        // 连接清理
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                // 清理过期的连接
                expireClean();
            }
        }, 0, 60, TimeUnit.SECONDS);

        // dns监测
        dnsChecker = new DnsChecker(hostPairs);
    }

    public Credis getResource() {
        usingCount.incrementAndGet();
        Credis credis = loopGet();
        credis.update();
        return credis;
    }

    private Credis loopGet() {
        Credis credis = null;
        while (credis == null || !dnsCheck(credis)) {
            ShardedJedis shardedJedis = shardedPool.getResource();
            if (!jedisCache.containsKey(shardedJedis)) {
                jedisCache.put(shardedJedis, new Credis(shardedJedis));
            }
            credis = jedisCache.get(shardedJedis);
            credis.setSlowThreshold(slowThreshold);
        }
        return credis;
    }

    private boolean dnsCheck(Credis credis) {
        boolean ok = true;
        for (Entry<String, String> entry : credis.getHostPairs().entrySet()) {
            if (!(ok &= dnsChecker.check(entry.getKey(), entry.getValue()))) {
                returnBrokenResource(credis);
                break;
            }
        }
        return ok;
    }

    public void returnBrokenResource(Credis credis) {
        usingCount.decrementAndGet();
        errorCount.incrementAndGet();
        credis.getShardedJedis().close();
       // shardedPool.returnBrokenResource(credis.getShardedJedis());
        jedisCache.remove(credis.getShardedJedis());

    }

    public void returnResource(Credis credis) {
        usingCount.decrementAndGet();
        credis.getShardedJedis().close();
//        shardedPool.returnResource(credis.getShardedJedis());
        jedisCache.remove(credis.getShardedJedis());
    }

    @JmxOperation
    public String getHosts() {
        StringBuffer stringBuffer = new StringBuffer();
        for (HostPair hostPair : hostPairs) {
            stringBuffer.append(hostPair.toString()).append(" | ");
        }
        return stringBuffer.toString();
    }

    @JmxOperation
    public void addHost(String host, int port, int timeout) {
        hostPairs.add(new HostPair(host, port, timeout));
        initialize();
    }

    public void destroy() {
        errorCount.set(0);
        usingCount.set(0);
        shardedPool.destroy();
    }

    @JmxOperation
    public void reset() {
        errorCount.set(0);
        usingCount.set(0);
    }

    public boolean isUsed() {
        return isUsed;
    }

    public AtomicInteger getUsingCount() {
        return usingCount;
    }

    public AtomicInteger getErrorCount() {
        return errorCount;
    }
}
