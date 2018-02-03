package com.cmh.dao;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;

/**
*@author: Menghui Chen
*@version: 2018年2月2日下午4:17:14
**/
@SuppressWarnings("rawtypes")
@Repository
public class RedisDao {
    @Autowired
    private StringRedisTemplate redisTemplate;
    public Object get(String key) {

        ValueOperations vo = redisTemplate.opsForValue();
        Object o = vo.get(key);
        return o;
    }
    
    public void set(String key, Object value) {
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set(key, value);
    }
    
    public void hset(String key, Map<String, ? extends Object> map) {
        HashOperations ho = redisTemplate.opsForHash();
        ho.putAll(key, map);
    }
    public void hsetValue(String key, Object hashKey, Object value) {
        HashOperations ho = redisTemplate.opsForHash();
        ho.put(key, hashKey, value);
    }
    public Object hget(String mapName, String key) {
        HashOperations ho= redisTemplate.opsForHash();
        Object o = null;
        if (ho.hasKey(mapName, key)) {
            o = ho.get(mapName, key);
        }
        return o;
    }
    
    public void sadd(String key, Object o) {
        SetOperations so = redisTemplate.opsForSet();
        so.add(key, o);
    }
    
    public void zadd(String key, Object o, double score) {
        ZSetOperations zo = redisTemplate.opsForZSet();
        zo.add(key, o, score);
    }
    
    public Set getTop(String key, int top) {
        ZSetOperations zo = redisTemplate.opsForZSet();
        Set set = zo.reverseRange(key, 0, top - 1);
        System.out.println(set.size());
        return set;
    }
    public void zincrby(String key, String memeber, double score) {
        ZSetOperations zo = redisTemplate.opsForZSet();
        zo.incrementScore(key, "member", score);
    }
    
    public Set<TypedTuple<Object>> zRangeWithScores(String key, int start, int end) {
        ZSetOperations zo = redisTemplate.opsForZSet();
        Set<TypedTuple<Object>> set = zo.rangeWithScores(key, start, end);
        return set;
    }
    public Long zrem(String key, Object... member) {
        ZSetOperations zo = redisTemplate.opsForZSet();
        return zo.remove(key, member);
    }
    public Long zRemRangeByRang(String key, int start, int end) {
        ZSetOperations zo = redisTemplate.opsForZSet();
        return zo.removeRange(key, start, end);
    }
}
