package com.bluecaf.cquec.controller

import com.bluecaf.cquec.dto.QueResponseDTO
import com.bluecaf.cquec.service.CookieService
import com.bluecaf.cquec.service.RedisService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QueController(
    private val cookieService: CookieService,
    private val redisService: RedisService
) {
    @GetMapping("/que-enter")
    fun enter(response: HttpServletResponse, request: HttpServletRequest): QueResponseDTO {
        val clientCookie = cookieService.find(request)

        if (clientCookie != null) {
            val waitingPosition = redisService.getWaitingPosition(clientCookie)
            if (waitingPosition != null) {
                return QueResponseDTO(2002, "waiting", waitingPosition)
            }
            if (redisService.isProcessed(clientCookie)) {
                return QueResponseDTO(2003, "processed", null)
            }
        }

        val queueSize = redisService.getQueueSize()
        val processedCount = redisService.getProcessedCount()
        if (queueSize == 0L && processedCount < 100L) {
            // 대기열 비어있으면 바로 처리
            val id = cookieService.generateUUIDv7()
            val cookie = cookieService.create(id)
            redisService.markAsProcessed(id)
            redisService.incrementProcessed()
            response.addCookie(cookie)
            return QueResponseDTO(2004, "entered", null)
        }

        val id = cookieService.generateUUIDv7()
        val cookie = cookieService.create(id)
        redisService.enqueue(id)
        redisService.resetProcessedCount()
        response.addCookie(cookie)
        return QueResponseDTO(2001, "created", null)
    }

    @GetMapping("/reset-redis")
    fun resetRedis() {
        redisService.clearAllKeys()
    }

    @GetMapping("/que-wait-test")
    fun waitTest() {

        repeat(50) {
            val id = cookieService.generateUUIDv7()
            redisService.enqueue(id)
        }
    }
}