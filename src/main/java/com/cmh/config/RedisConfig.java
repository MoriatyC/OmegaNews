package com.cmh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.cmh.domain.News;
import com.cmh.utils.RedisObjectSerializer;

/**
*@author: Menghui Chen
*@version: 2018年2月2日下午3:24:10
**/
@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, News> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, News> template = new RedisTemplate<String, News>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        template.setEnableTransactionSupport(true);
        return template;
    }


}