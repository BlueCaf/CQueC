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

    // 대기열 수 확인
    fun getQueueSize(): Long {
        return stringRedisTemplate.opsForZSet().zCard("queue:waiting:zset") ?: 0L
    }

    // 처리 완료
    fun markAsProcessed(id: String) {
        stringRedisTemplate.opsForValue().set("queue:processed:$id", "1", Duration.ofMinutes(30))
    }

    // 초기화
    fun clearAllKeys() {
        stringRedisTemplate.execute { it.serverCommands().flushDb() }
    }
}