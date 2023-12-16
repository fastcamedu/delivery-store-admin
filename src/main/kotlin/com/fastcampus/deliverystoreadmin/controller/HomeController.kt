package com.fastcampus.deliverystoreadmin.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @GetMapping("/", "/home")
    fun index(model: Model): String {
        logger.info { ">>> home" }

        if (SecurityContextHolder.getContext().authentication != null) {
            return "redirect:/dashboard"
        }

        return "home"
    }
}