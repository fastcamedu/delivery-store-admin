package com.fastcampus.deliverystoreadmin.security

import com.fastcampus.deliverystoreadmin.common.HttpConstants.Companion.COOKIE_NAME_ACCESS_TOKEN
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtExceptionFilter: OncePerRequestFilter() {

    companion object {
        val logger = KotlinLogging.logger(this::class.java.name)
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            JwtExceptionFilter.logger.info { ">>> doFilterInternal, request: ${request.requestURI}" }
            filterChain.doFilter(request, response)
        } catch (ex: ExpiredJwtException) {
            removeAccessTokenCookie(response)
            setErrorResponse(response, ex)
        } catch (ex: JwtException) {
            removeAccessTokenCookie(response)
            setErrorResponse(response, ex)
        }
    }

    private fun removeAccessTokenCookie(response: HttpServletResponse) {
        JwtExceptionFilter.logger.debug { ">>> removeAccessTokenCookie" }
        val cookie = Cookie(COOKIE_NAME_ACCESS_TOKEN, null)
        cookie.maxAge = 0
        cookie.path = "/"
        response.addCookie(cookie)
    }

    private fun setErrorResponse(res: HttpServletResponse, ex: Throwable) {
        res.sendRedirect("/")
    }
}