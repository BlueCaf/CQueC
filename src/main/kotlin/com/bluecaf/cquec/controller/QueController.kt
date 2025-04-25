package com.bluecaf.cquec.controller

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
    fun enter(response: HttpServletResponse, request: HttpServletRequest): String {
        var responseString = "already"
        val clientCookie = cookieService.find(request)

        if (redisService.getValue(clientCookie).toString() == "null") {
            val id = cookieService.generateUUIDv7()
            val cookie = cookieService.create(id)
            redisService.setValue(id, "1")
            // 응답에 쿠키 추가
            response.addCookie(cookie)
            responseString = "created"
        }

        return responseString
    }

//    @GetMapping("/test2")
//    fun getCookieFromRedis(): String {
//        return redisService.getValue("1").toString()
//    }
//
//    @GetMapping("/test3")
//    fun getCookieFromClient(request: HttpServletRequest): String {
//        return cookieService.find(request)
//    }
//
//    @GetMapping("/test4")
//    fun test33(request: HttpServletRequest): String? {
//        return cookieService.find(request)
//    }
}