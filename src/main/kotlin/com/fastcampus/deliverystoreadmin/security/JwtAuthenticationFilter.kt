package com.fastcampus.deliverystoreadmin.security

import com.fastcampus.deliverystoreadmin.common.HttpConstants
import com.fastcampus.deliverystoreadmin.service.auth.DeliveryStoreTokenService
import com.fastcampus.deliverystoreadmin.service.store.StoreDetailsService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val storeDetailsService: StoreDetailsService,
    private val deliveryStoreTokenService: DeliveryStoreTokenService,
) : OncePerRequestFilter() {

    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessTokenCookie = request.cookies?.firstOrNull { it.name == HttpConstants.COOKIE_NAME_ACCESS_TOKEN }
        if (accessTokenCookie != null) {
            JwtAuthenticationFilter.logger.debug {">>> jwtToken: ${accessTokenCookie.value}, requestUrl: ${request.requestURI}" }
            val jwtToken = accessTokenCookie.value
            val email = deliveryStoreTokenService.extractEmail(jwtToken)
            if (email != null && SecurityContextHolder.getContext().authentication == null) {
                val foundUser = storeDetailsService.loadUserByUsername(email)
                if (deliveryStoreTokenService.isValid(jwtToken, foundUser)) {
                    JwtExceptionFilter.logger.debug { ">>> jwtToken is valid" }
                    updateContext(foundUser, request)
                }
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun updateContext(foundUser: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
    }
}