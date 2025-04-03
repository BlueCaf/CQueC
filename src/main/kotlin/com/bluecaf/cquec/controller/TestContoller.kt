package com.bluecaf.cquec.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestContoller {

    @GetMapping("/test")
    fun test(): String {
        return "test"
    }
}