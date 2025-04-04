package com.bluecaf.cquec.controller

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class TestContoller {

    @GetMapping("/test")
    fun test(): String {
        return "test"
    }

    @GetMapping("/test1")
    fun test1(response: HttpServletResponse): String {

        val cookie = Cookie("user", "JohnDoe")
        cookie.maxAge = 60 * 30 // 쿠키 유효시간 1시간
        cookie.isHttpOnly = true // 클라이언트 측 자바스크립트에서 쿠키에 접근하지 못하게 설정
        cookie.secure = true // HTTPS 연결에서만 쿠키를 사용하도록 설정

        val cookie1 = Cookie("id", generateUUIDv7())
        cookie1.maxAge = 60 * 30 // 쿠키 유효시간 1시간
        cookie1.isHttpOnly = true // 클라이언트 측 자바스크립트에서 쿠키에 접근하지 못하게 설정
        cookie1.secure = true // HTTPS 연결에서만 쿠키를 사용하도록 설정

        // 응답에 쿠키 추가
        response.addCookie(cookie)
        response.addCookie(cookie1)

        return "쿠키가 생성되었습니다."
    }

    fun generateUUIDv7(): String {
        // 현재 시간을 밀리초 단위로 가져옵니다.
        val timestamp = Instant.now().toEpochMilli()
        // 랜덤 값을 생성합니다.
        val randomPart = UUID.randomUUID().toString().replace("-", "").take(8) // 랜덤 8자리
        // UUIDv7 형식: [타임스탬프(밀리초) + 랜덤 부분]
        return "$timestamp-$randomPart"
    }
}