package com.bluecaf.cquec.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class QueueViewController {

    @GetMapping("/queue")
    fun showQueuePage(): String {
        return "queue"
    }

    @GetMapping("/enter")
    fun showEnterPage(): String {
        return "enter"
    }
}