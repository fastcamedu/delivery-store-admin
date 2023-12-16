package com.fastcampus.deliverystoreadmin.security

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class CustomAccessDeniedHandler: AccessDeniedHandler {
    companion object {
        internal val logger = KotlinLogging.logger(this::class.java.name)
    }
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        logger.info { ">>> 접근이 거부되었습니다." }
        response?.sendRedirect("/")
    }
}