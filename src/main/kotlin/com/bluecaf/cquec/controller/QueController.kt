package com.bluecaf.cquec.controller

import com.bluecaf.cquec.service.CookieService
import com.bluecaf.cquec.service.RedisService
import com.bluecaf.cquec.util.CryptoUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QueController(
    private val cookieService: CookieService,
    private val redisService: RedisService
) {
    @GetMapping("/test1")
    fun queueing(response: HttpServletResponse): String {
        val id = cookieService.generateUUIDv7()
        val cookie = cookieService.create(CryptoUtil.encryptWithHmac(id))

        redisService.setValue("1", id)
        // 응답에 쿠키 추가
        response.addCookie(cookie)

        return "Created Cookie"
    }

    @GetMapping("/test2")
    fun getCookieFromRedis(): String {
        return redisService.getValue("1").toString()
    }

    @GetMapping("/test3")
    fun getCookieFromClient(request: HttpServletRequest): String {
        return cookieService.find(request)
    }
    
    @GetMapping("/test4")
    fun test33(request: HttpServletRequest): String? {
        return CryptoUtil.decryptWithHmacCheck(cookieService.find(request))
    }
}