package com.hongyu.revaluation.common;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author JaikenWong
 * @since 2024-02-02 11:28
 **/
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        // 这个connectionFactory的Bean是哪里定义的：boot帮忙创建的
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // key序列化：String~
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // value序列化：obj2json
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

    @Bean("sessionRedisTemplate")
    public RedisTemplate<Serializable, Session> sessionRedisTemplate(RedisConnectionFactory connectionFactory) {
        // 这个connectionFactory的Bean是哪里定义的：boot帮忙创建的
        RedisTemplate<Serializable, Session> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // key序列化：String~
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        return redisTemplate;
    }

    @Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> jsonRedisSerializer() {
        // 为了适应Shiro 的会话信息序列化和反序列化
        return new JdkSerializationRedisSerializer();
    }
}