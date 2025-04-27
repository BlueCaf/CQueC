package com.bluecaf.cquec.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class QueueViewController {

    @GetMapping("/queue")
    fun showQueuePage(): String {
        return "forward:/queue.html"
    }

    @GetMapping("/enter")
    fun showEnterPage(): String {
        return "forward:/enter.html"
    }
}