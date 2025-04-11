package com.bluecaf.cquec.service

import jakarta.servlet.http.Cookie
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class CookieService {

    fun create(id: String): Cookie {
        val cookie = Cookie("id", id)
        cookie.maxAge = 60 * 30 // 쿠키 유효시간 30분
        cookie.isHttpOnly = true // 클라이언트 측 자바스크립트에서 쿠키에 접근하지 못하게 설정
        cookie.secure = true // HTTPS 연결에서만 쿠키를 사용하도록 설정

        return cookie
    }

    fun refresh() {

    }

    fun find() {

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