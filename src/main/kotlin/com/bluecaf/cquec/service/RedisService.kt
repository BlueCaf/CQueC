package com.bluecaf.cquec.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService(
    private val stringRedisTemplate: StringRedisTemplate,
) {
    fun enqueue(userId: String) {
        val currentTimestamp = System.currentTimeMillis()
        stringRedisTemplate.opsForZSet().add("queue:waiting:zset", userId, currentTimestamp.toDouble())
    }

    fun getWaitingPosition(userId: String): Long? {
        return stringRedisTemplate.opsForZSet().rank("queue:waiting:zset", userId)
    }

    // 이미 처리했는지 확인
    fun isProcessed(userId: String): Boolean {
        return stringRedisTemplate.hasKey("queue:processed:$userId") == true
    }

    // 초기화
    fun clearAllKeys() {
        stringRedisTemplate.execute { it.serverCommands().flushDb() }
    }
}