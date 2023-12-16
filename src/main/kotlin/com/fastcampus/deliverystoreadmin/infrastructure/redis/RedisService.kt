package com.fastcampus.deliverystoreadmin.infrastructure.redis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisService(private val redisTemplate: StringRedisTemplate) {

    fun writeToRedis(key: String, value: String) {
        redisTemplate.opsForValue().set(key, value)
        redisTemplate.expire(key, 30, TimeUnit.MINUTES)
    }

    fun readFromRedis(key: String): String? {
        return redisTemplate.opsForValue().get(key)
    }
}