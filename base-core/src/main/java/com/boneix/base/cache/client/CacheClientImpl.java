/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2014-8-23
 * Description:RedisCacheImpl.java 
 */
package com.boneix.base.cache.client;

import com.boneix.base.cache.jmx.CacheMonitor;
import com.boneix.base.cache.pool.Credis;
import com.boneix.base.cache.pool.HostPair;
import com.boneix.base.cache.pool.ShardedPool;
import com.boneix.base.cache.template.CacheCallback;
import com.boneix.base.cache.template.CacheTemplate;
import com.boneix.base.cache.util.CacheClientUtils;
import com.boneix.base.cache.util.Commands;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author wangchong
 */
public class CacheClientImpl extends CacheMonitor implements CacheClient {

    private final CacheTemplate template;

    private static String EMPTY = "";

    public CacheClientImpl(JedisPoolConfig poolConfig, List<HostPair> hostPairs, int dbIndex) {
        super(poolConfig, hostPairs);
        template = new CacheTemplate(this, dbIndex);
    }

    public CacheClientImpl(JedisPoolConfig poolConfig, List<HostPair> hostPairs, int dbIndex, boolean isThrow) {
        super(poolConfig, hostPairs);
        template = new CacheTemplate(this, dbIndex, isThrow);
    }

    public CacheClientImpl(ShardedPool shardedPool, int dbIndex, boolean isThrow) {
        super(shardedPool);
        template = new CacheTemplate(this, dbIndex, isThrow);
    }

    public CacheClientImpl(ShardedPool shardedPool, int dbIndex) {
        super(shardedPool);
        template = new CacheTemplate(this, dbIndex);
    }

    protected CacheTemplate getTemplate() {
        return template;
    }

    @Override
    public String set(final String key, final String value) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).set(key, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SET, key);
            }
        });
    }

    @Override
    public String get(final String key) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).get(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.GET, key);
            }
        });
    }

    @Override
    public Boolean exists(final String key) {
        return template.execute(new CacheCallback<Boolean>() {
            @Override
            public Boolean doInAction(Credis jedis) {
                return jedis.getJedis(key).exists(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.EXISTS, key);
            }
        });
    }

    @Override
    public Long expire(final String key, final int seconds) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).expire(key, seconds);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.EXISTS, key);
            }
        });
    }

    @Override
    public Long expireAt(final String key, final long unixTime) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).expireAt(key, unixTime);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.EXPIREAT, key);
            }
        });
    }

    @Override
    public Long ttl(final String key) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).ttl(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.TTL, key);
            }
        });
    }

    @Override
    public Boolean setbit(final String key, final long offset, final boolean value) {
        return template.execute(new CacheCallback<Boolean>() {
            @Override
            public Boolean doInAction(Credis jedis) {
                return jedis.getJedis(key).setbit(key, offset, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SETBIT, key);
            }
        });
    }

    @Override
    public Boolean getbit(final String key, final long offset) {
        return template.execute(new CacheCallback<Boolean>() {
            @Override
            public Boolean doInAction(Credis jedis) {
                return jedis.getJedis(key).getbit(key, offset);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.GETBIT, key);
            }
        });
    }

    @Override
    public Long setrange(final String key, final long offset, final String value) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).setrange(key, offset, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SETRANGE, key);
            }
        });

    }

    @Override
    public String getrange(final String key, final long startOffset, final long endOffset) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).getrange(key, startOffset, endOffset);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.GETRANGE, key);
            }
        });
    }

    @Override
    public String getSet(final String key, final String value) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).getSet(key, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.GETSET, key);
            }
        });
    }

    @Override
    public Long setnx(final String key, final String value) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).setnx(key, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SETNX, key);
            }
        });
    }

    @Override
    public String setex(final String key, final int seconds, final String value) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).setex(key, seconds, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SETEX, key);
            }
        });
    }

    @Override
    public Long decrBy(final String key, final long integer) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).decrBy(key, integer);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.DECRBY, key);
            }
        });
    }

    @Override
    public Long decr(final String key) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).decr(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.DECR, key);
            }
        });
    }

    @Override
    public Long incrBy(final String key, final long integer) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).incrBy(key, integer);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.INCRBY, key);
            }
        });
    }

    @Override
    public Long incr(final String key) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).incr(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.INCR, key);
            }
        });
    }

    @Override
    public Long append(final String key, final String value) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).append(key, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.APPEND, key);
            }
        });
    }

    @Override
    public String substr(final String key, final int start, final int end) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).substr(key, start, end);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SUBSTR, key);
            }
        });
    }

    @Override
    public Long hset(final String key, final String field, final String value) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).hset(key, field, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HSET, key);
            }
        });
    }

    @Override
    public String hget(final String key, final String field) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).hget(key, field);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HGET, key);
            }
        });
    }

    @Override
    public Long hsetnx(final String key, final String field, final String value) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).hsetnx(key, field, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HSETNX, key);
            }
        });
    }

    @Override
    public String hmset(final String key, final Map<String, String> hash) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).hmset(key, hash);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HMSET, key);
            }
        });
    }

    @Override
    public List<String> hmget(final String key, final String... fields) {
        return template.execute(new CacheCallback<List<String>>() {
            @Override
            public List<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).hmget(key, fields);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HMGET, key);
            }
        });
    }

    @Override
    public Long hincrBy(final String key, final String field, final long value) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).hincrBy(key, field, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HINCRBY, key);
            }
        });
    }

    @Override
    public Boolean hexists(final String key, final String field) {
        return template.execute(new CacheCallback<Boolean>() {
            @Override
            public Boolean doInAction(Credis jedis) {
                return jedis.getJedis(key).hexists(key, field);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HEXISTS, key);
            }
        });
    }

    @Override
    public Long hdel(final String key, final String... field) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).hdel(key, field);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HDEL, key);
            }
        });
    }

    @Override
    public Long hlen(final String key) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).hlen(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HLEN, key);
            }
        });
    }

    @Override
    public Set<String> hkeys(final String key) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).hkeys(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HKEYS, key);
            }
        });
    }

    @Override
    public List<String> hvals(final String key) {
        return template.execute(new CacheCallback<List<String>>() {
            @Override
            public List<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).hvals(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HVALS, key);
            }
        });
    }

    @Override
    public Map<String, String> hgetAll(final String key) {
        return template.execute(new CacheCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInAction(Credis jedis) {
                return jedis.getJedis(key).hgetAll(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.HGETALL, key);
            }
        });
    }

    @Override
    public Long rpush(final String key, final String... string) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).rpush(key, string);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.RPUSH, key);
            }
        });
    }

    @Override
    public Long lpush(final String key, final String... string) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).lpush(key, string);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.LPUSH, key);
            }
        });
    }

    @Override
    public Long llen(final String key) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).llen(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.LLEN, key);
            }
        });
    }

    @Override
    public List<String> lrange(final String key, final long start, final long end) {
        return template.execute(new CacheCallback<List<String>>() {
            @Override
            public List<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).lrange(key, start, end);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.LRANGE, key);
            }
        });
    }

    @Override
    public String ltrim(final String key, final long start, final long end) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).ltrim(key, start, end);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.LTRIM, key);
            }
        });
    }

    @Override
    public String lindex(final String key, final long index) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).lindex(key, index);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.LINDEX, key);
            }
        });
    }

    @Override
    public String lset(final String key, final long index, final String value) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).lset(key, index, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.LSET, key);
            }
        });
    }

    @Override
    public Long lrem(final String key, final long count, final String value) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).lrem(key, count, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.LREM, key);
            }
        });
    }

    @Override
    public String lpop(final String key) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).lpop(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.LPOP, key);
            }
        });
    }

    @Override
    public String rpop(final String key) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).rpop(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.RPOP, key);
            }
        });
    }

    @Override
    public Long sadd(final String key, final String... member) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).sadd(key, member);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SADD, key);
            }
        });
    }

    @Override
    public Set<String> smembers(final String key) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).smembers(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SMEMBERS, key);
            }
        });
    }

    @Override
    public Long srem(final String key, final String... member) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).srem(key, member);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SREM, key);
            }
        });
    }

    @Override
    public String spop(final String key) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).spop(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SPOP, key);
            }
        });
    }

    @Override
    public Long scard(final String key) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).scard(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SCARD, key);
            }
        });
    }

    @Override
    public Boolean sismember(final String key, final String member) {
        return template.execute(new CacheCallback<Boolean>() {
            @Override
            public Boolean doInAction(Credis jedis) {
                return jedis.getJedis(key).sismember(key, member);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SISMEMBER, key);
            }
        });
    }

    @Override
    public String srandmember(final String key) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.getJedis(key).srandmember(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SRANDMEMBER, key);
            }
        });
    }

    @Override
    public Long zadd(final String key, final double score, final String member) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).zadd(key, score, member);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZADD, key);
            }
        });
    }

    @Override
    public Long zadd(final String key, final Map<String, Double> scoreMembers) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).zadd(key, scoreMembers);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZADD, key);
            }
        });
    }

    @Override
    public Set<String> zrange(final String key, final long start, final long end) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrange(key, start, end);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZRANGE, key);
            }
        });
    }

    @Override
    public Long zrem(final String key, final String... member) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).zrem(key, member);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREM, key);
            }
        });
    }

    @Override
    public Double zincrby(final String key, final double score, final String member) {
        return template.execute(new CacheCallback<Double>() {
            @Override
            public Double doInAction(Credis jedis) {
                return jedis.getJedis(key).zincrby(key, score, member);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZINCRBY, key);
            }
        });
    }

    @Override
    public Long zrank(final String key, final String member) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).zrank(key, member);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZRANK, key);
            }
        });
    }

    @Override
    public Long zrevrank(final String key, final String member) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).zrevrank(key, member);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREVRANK, key);
            }
        });
    }

    @Override
    public Set<String> zrevrange(final String key, final long start, final long end) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrevrange(key, start, end);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREVRANGE, key);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeWithScores(final String key, final long start, final long end) {
        return template.execute(new CacheCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrangeWithScores(key, start, end);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZRANGEWITHSCORES, key);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long end) {
        return template.execute(new CacheCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrevrangeWithScores(key, start, end);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREVRANGEWITHSCORES, key);
            }
        });
    }

    @Override
    public Long zcard(final String key) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).zcard(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZCARD, key);
            }
        });
    }

    @Override
    public Double zscore(final String key, final String member) {
        return template.execute(new CacheCallback<Double>() {
            @Override
            public Double doInAction(Credis jedis) {
                return jedis.getJedis(key).zscore(key, member);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZSCORE, key);
            }
        });
    }

    @Override
    public List<String> sort(final String key) {
        return template.execute(new CacheCallback<List<String>>() {
            @Override
            public List<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).sort(key);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SORT, key);
            }
        });
    }

    @Override
    public List<String> sort(final String key, final SortingParams sortingParameters) {
        return template.execute(new CacheCallback<List<String>>() {
            @Override
            public List<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).sort(key, sortingParameters);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.SORT, key);
            }
        });
    }

    @Override
    public Long zcount(final String key, final double min, final double max) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).zcount(key, min, max);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZCOUNT, key);
            }
        });
    }

    @Override
    public Long zcount(final String key, final String min, final String max) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).zcount(key, min, max);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZCOUNT, key);
            }
        });
    }

    @Override
    public Set<String> zrangeByScore(final String key, final double min, final double max) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrangeByScore(key, min, max);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZRANGEBYSCORE, key);
            }
        });
    }

    @Override
    public Set<String> zrangeByScore(final String key, final String min, final String max) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrangeByScore(key, min, max);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZRANGEBYSCORE, key);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByScore(final String key, final double min, final double max) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrevrangeByScore(key, min, max);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREVRANGEBYSCORE, key);
            }
        });
    }

    @Override
    public Set<String> zrangeByScore(final String key, final double min, final double max, final int offset,
                                     final int count) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrangeByScore(key, min, max, offset, count);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZRANGEBYSCORE, key);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByScore(final String key, final String min, final String max) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrevrangeByScore(key, min, max);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREVRANGEBYSCORE, key);
            }
        });
    }

    @Override
    public Set<String> zrangeByScore(final String key, final String min, final String max, final int offset,
                                     final int count) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrangeByScore(key, min, max, offset, count);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZRANGEBYSCORE, key);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByScore(final String key, final double min, final double max, final int offset,
                                        final int count) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrevrangeByScore(key, max, min, offset, count);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREVRANGEBYSCORE, key);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max) {
        return template.execute(new CacheCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrangeByScoreWithScores(key, min, max);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZRANGEBYSCOREWITHSCORES, key);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double min, final double max) {
        return template.execute(new CacheCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrevrangeByScoreWithScores(key, max, min);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREVRANGEBYSCOREWITHSCORES, key);
            }

        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max, final int offset,
                                              final int count) {
        return template.execute(new CacheCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrangeByScoreWithScores(key, min, max, offset, count);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZRANGEBYSCOREWITHSCORES, key);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByScore(final String key, final String min, final String max, final int offset,
                                        final int count) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrevrangeByScore(key, min, max, offset, count);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREVRANGEBYSCORE, key);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max) {
        return template.execute(new CacheCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrangeByScoreWithScores(key, min, max);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZRANGEBYSCOREWITHSCORES, key);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String min, final String max) {
        return template.execute(new CacheCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrevrangeByScoreWithScores(key, min, max);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREVRANGEBYSCOREWITHSCORES, key);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max, final int offset,
                                              final int count) {
        return template.execute(new CacheCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrangeByScoreWithScores(key, min, max, offset, count);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZRANGEBYSCOREWITHSCORES, key);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double min, final double max,
                                                 final int offset, final int count) {
        return template.execute(new CacheCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrevrangeByScoreWithScores(key, max, min, offset, count);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREVRANGEBYSCOREWITHSCORES, key);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String min, final String max,
                                                 final int offset, final int count) {
        return template.execute(new CacheCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInAction(Credis jedis) {
                return jedis.getJedis(key).zrevrangeByScoreWithScores(key, max, min, offset, count);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREVRANGEBYSCOREWITHSCORES, key);
            }
        });
    }

    @Override
    public Long zremrangeByRank(final String key, final long start, final long end) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).zremrangeByRank(key, start, end);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREMRANGEBYRANK, key);
            }
        });
    }

    @Override
    public Long zremrangeByScore(final String key, final double start, final double end) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).zremrangeByScore(key, start, end);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREMRANGEBYSCORE, key);
            }
        });
    }

    @Override
    public Long zremrangeByScore(final String key, final String start, final String end) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).zremrangeByScore(key, start, end);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.ZREMRANGEBYSCORE, key);
            }
        });
    }

    @Override
    public Long linsert(final String key, final LIST_POSITION where, final String pivot, final String value) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).linsert(key, where, pivot, value);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.LINSERT, key);
            }
        });
    }

    @Override
    public Long lpushx(final String key, final String string) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).lpushx(key, string);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.LPUSHX, key);
            }
        });
    }

    @Override
    public Long rpushx(final String key, final String string) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis jedis) {
                return jedis.getJedis(key).rpushx(key, string);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.RPUSHX, key);
            }
        });
    }

    @Override
    public Long del(final String... keys) {
        return template.execute(new CacheCallback<Long>() {
            @Override
            public Long doInAction(Credis credis) {
                Map<Jedis, List<String>> groupJedis = new HashMap<Jedis, List<String>>();
                for (String key : keys) {
                    Jedis jedis = credis.getJedis(key);
                    if (!groupJedis.containsKey(jedis)) {
                        groupJedis.put(jedis, new ArrayList<String>());
                    }
                    groupJedis.get(jedis).add(key);
                }
                long result = 0L;
                for (Entry<Jedis, List<String>> entry : groupJedis.entrySet()) {
                    result += entry.getKey().del(entry.getValue().toArray(new String[entry.getValue().size()]));
                }
                return result;
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.DEL, keys);
            }
        });
    }

    @Override
    public String mset(final Map<String, String> keysvalues) {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis credis) {
                Map<Jedis, List<String>> groupJedis = new HashMap<Jedis, List<String>>();
                for (Entry<String, String> entry : keysvalues.entrySet()) {
                    Jedis jedis = credis.getJedis(entry.getKey());
                    if (!groupJedis.containsKey(jedis)) {
                        groupJedis.put(jedis, new ArrayList<String>());
                    }
                    groupJedis.get(jedis).add(entry.getKey());
                    groupJedis.get(jedis).add(entry.getValue());
                }

                StringBuffer buffer = new StringBuffer();
                for (Entry<Jedis, List<String>> entry : groupJedis.entrySet()) {
                    buffer.append((entry.getKey().mset(entry.getValue().toArray(new String[entry.getValue().size()]))))
                            .append(",");
                }
                return buffer.toString();
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.MSET, keysvalues.keySet().toArray());
            }
        });
    }

    @Override
    public List<String> mget(final String... keys) {
        return template.execute(new CacheCallback<List<String>>() {
            @Override
            public List<String> doInAction(Credis credis) {
                Map<Jedis, List<String>> groupJedis = new HashMap<Jedis, List<String>>();
                for (String key : keys) {
                    Jedis jedis = credis.getJedis(key);
                    if (!groupJedis.containsKey(jedis)) {
                        groupJedis.put(jedis, new ArrayList<String>());
                    }
                    groupJedis.get(jedis).add(key);
                }
                List<String> result = new ArrayList<String>();
                for (Entry<Jedis, List<String>> entry : groupJedis.entrySet()) {
                    result.addAll(entry.getKey().mget(entry.getValue().toArray(new String[entry.getValue().size()])));
                }
                return result;
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.MGET, keys);
            }
        });
    }

    @Override
    public String flushDB() {
        return template.execute(new CacheCallback<String>() {
            @Override
            public String doInAction(Credis jedis) {
                return jedis.flushDB();
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.FLUSHDB, EMPTY);
            }
        });
    }

    @Override
    public Set<String> keys(final String pattern) {
        return template.execute(new CacheCallback<Set<String>>() {
            @Override
            public Set<String> doInAction(Credis jedis) {
                return jedis.keys(pattern);
            }

            @Override
            public String getIdentity() {
                return CacheClientUtils.generateIdentity(Commands.KEYS, pattern);
            }
        });
    }

}
