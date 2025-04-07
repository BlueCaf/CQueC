package com.bluecaf.cquec.controller

import com.bluecaf.cquec.service.CookieService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QueController(private val cookieService: CookieService) {

    @GetMapping("/test1")
    fun queueing(response: HttpServletResponse): String {
        val cookie = cookieService.create()
        // 응답에 쿠키 추가
        response.addCookie(cookie)

        return "Created Cookie"
    }
}