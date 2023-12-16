package com.fastcampus.deliverystoreadmin.security

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
): AuthenticationEntryPoint {

    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)
    }

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        logger.info { ">>> 인증이 실패하였습니다, ${authException.message}" }
        response.sendRedirect("/auth/login")
    }
}