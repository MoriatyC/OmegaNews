package com.cmh.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cmh.dao.RedisDao;

/**
*@author: Menghui Chen
*@version: 2018年2月3日下午2:13:40
**/
@Service
public class Rank {
    @Autowired
    private RedisDao redisDao;
    private final int ONE_WEEK_IN_SECONDS = 7 * 86400;
    @Scheduled(cron = "0 0 0 1/7 * ?")
    public void updataRank() {
        redisDao.zRemRangeByRang("time:", 0, -1);
        long cutOff = System.currentTimeMillis() / 1000 - ONE_WEEK_IN_SECONDS;
        Set<TypedTuple<Object>> set = redisDao.zRangeWithScores("time:", 0, -1);
        for (TypedTuple<Object> o: set) {
            //如果过期直接删除，否则计算结果
            if (Long.valueOf(o.getScore().toString()) < cutOff) {
                redisDao.zrem("time:", o.getValue());
            } else {
                redisDao.zadd("score:", o.getValue(), 
                        Double.valueOf(o.getValue().toString()) + 
                        432 * Double.valueOf(redisDao.hget(o.getValue().toString(), "commentCount").toString()));
            }
        }
    }
}
