package com.bluecaf.cquec.service

import com.bluecaf.cquec.util.Crypto
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service

@Service
class CookieService {

    fun create(id: String): Cookie {
        val cookie = Cookie("cquecId", Crypto.encryptWithHmac(id))
        //cookie.maxAge = 60 * 30 // 쿠키 유효시간 30분
        cookie.isHttpOnly = true // 클라이언트 측 자바스크립트에서 쿠키에 접근하지 못하게 설정
        cookie.secure = false // HTTPS 연결에서만 쿠키를 사용하도록 설정

        return cookie
    }

    fun find(request: HttpServletRequest): String? {
        return runCatching {
            val userIdCookie = request.cookies?.firstOrNull { it.name == "cquecId" } ?: return null
            Crypto.decryptWithHmacCheck(userIdCookie.value)
        }.getOrNull()
    }
}