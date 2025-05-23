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

    fun isProcessed(userId: String): Boolean {
        return stringRedisTemplate.hasKey("queue:processed:$userId") == true
    }

    fun getQueueSize(): Long {
        return stringRedisTemplate.opsForZSet().zCard("queue:waiting:zset") ?: 0L
    }

    fun markAsProcessed(userId: String) {
        stringRedisTemplate.opsForValue().set("queue:processed:$userId", "1", Duration.ofMinutes(30))
    }

    fun clearAllKeys() {
        stringRedisTemplate.execute { it.serverCommands().flushDb() }
    }

    fun incrementProcessed() {
        stringRedisTemplate.opsForValue().increment("queue:processed:count")
    }

    fun getProcessedCount(): Long {
        return stringRedisTemplate.opsForValue().get("queue:processed:count")?.toLong() ?: 0
    }

    fun resetProcessedCount() {
        stringRedisTemplate.delete("queue:processed:count")
    }
}