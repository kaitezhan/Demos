/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2014-8-23
 * Description:CacheClientUtils.java 
 */
package com.boneix.base.cache.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.boneix.base.cache.jmx.CacheMonitor;
import com.boneix.base.cache.pool.Credis;
import com.boneix.base.cache.pool.HostPair;

import redis.clients.jedis.JedisShardInfo;

/**
 * @author wangchong
 */
public class CacheClientUtils {

    private static final String LOG_Format = "CMD=> {0} - K(s)=> {1}";

    /**
     * 连接释放
     *
     * @param jedis
     */
    public static void release(CacheMonitor monitor, Credis jedis) {
        if (jedis != null) {
            monitor.returnResource(jedis);
        }
    }

    /**
     * 连接销毁
     *
     * @param jedis
     */
    public static void broken(CacheMonitor monitor, Credis jedis) {
        if (jedis != null) {
            monitor.returnBrokenResource(jedis);
        }
    }

    public static List<HostPair> convertHostPair(List<JedisShardInfo> shards) {
        List<HostPair> hostPairs = new ArrayList<HostPair>(shards.size());
        for (JedisShardInfo shardInfo : shards) {
            hostPairs.add(new HostPair(shardInfo.getHost(), shardInfo.getPort(), shardInfo.getConnectionTimeout()));
        }
        return hostPairs;
    }

    public static String generateIdentity(Commands method, Object... keys) {
        return MessageFormat.format(LOG_Format, method, arrayToString(keys));
    }

    private static String arrayToString(Object... keys) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < keys.length; i++) {
            buffer.append(keys[i]);
            if (i != keys.length - 1) {
                buffer.append(",");
            }
        }
        return buffer.toString();
    }

    public static String generateIdentity(Commands method, String... keys) {
        Object[] objs = Arrays.copyOfRange(keys, 0, keys.length, Object[].class);
        return generateIdentity(method, objs);
    }

    public static void main(String[] args) {
        System.out.println(generateIdentity(Commands.APPEND, "A", "B"));
    }
}
