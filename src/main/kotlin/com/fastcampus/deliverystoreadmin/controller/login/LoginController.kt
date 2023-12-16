package com.fastcampus.deliverystoreadmin.controller.login

import com.fastcampus.deliverystoreadmin.common.CookieUtils
import com.fastcampus.deliverystoreadmin.common.HttpConstants.Companion.COOKIE_NAME_ACCESS_TOKEN
import com.fastcampus.deliverystoreadmin.common.HttpConstants.Companion.COOKIE_NAME_STORE_ID
import com.fastcampus.deliverystoreadmin.controller.login.dto.DeliveryStoreTokenResponse
import com.fastcampus.deliverystoreadmin.controller.login.dto.LoginRequest
import com.fastcampus.deliverystoreadmin.controller.login.dto.RefreshTokenRequest
import com.fastcampus.deliverystoreadmin.service.auth.AuthenticationService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.util.internal.StringUtil
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ResponseStatusException

@Controller
class LoginController(
    private val authenticationService: AuthenticationService
) {
    companion object {
        private val logger = KotlinLogging.logger {  }
    }

    @GetMapping("/auth/login")
    fun loginPage(): String {
        logger.info { ">>> 로그인 페이지" }
        return "/auth/login"
    }

    @PostMapping("/auth/login-process")
    fun login(
        @ModelAttribute loginRequest: LoginRequest,
        httpServletResponse: HttpServletResponse,
        model: Model,
    ): String {
        logger.info { ">>> 로그인 요청: ${loginRequest.email}" }
        val loginResponse = authenticationService.authentication(loginRequest)

        val accessTokenCookie = CookieUtils.createAuthCookie(COOKIE_NAME_ACCESS_TOKEN, loginResponse.accessToken)
        val storeIdCookie = CookieUtils.createAuthCookie(COOKIE_NAME_STORE_ID, loginResponse.storeId.toString())
        httpServletResponse.addCookie(accessTokenCookie)
        httpServletResponse.addCookie(storeIdCookie)

        model.addAttribute("email", loginResponse.email)

        return  "redirect:/dashboard"
    }

    @PostMapping("/auth/logout")
    fun logout(
        httpServletResponse: HttpServletResponse,
    ): String {
        logger.info { ">>> Request logout" }
        val accessTokenCookie = CookieUtils.createAuthCookie(COOKIE_NAME_ACCESS_TOKEN, StringUtil.EMPTY_STRING)
        val storeIdCookie = CookieUtils.createAuthCookie(COOKIE_NAME_STORE_ID, StringUtil.EMPTY_STRING)
        httpServletResponse.addCookie(accessTokenCookie)
        httpServletResponse.addCookie(storeIdCookie)

        return "redirect:/"
    }

    @PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestBody request: RefreshTokenRequest
    ): DeliveryStoreTokenResponse {
        logger.info { ">>> Request refresh accessToken" }
        return authenticationService.refreshAccessToken(request.refreshToken)
            ?.mapToTokenResponse()
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token.")
    }

    private fun String.mapToTokenResponse(): DeliveryStoreTokenResponse =
        DeliveryStoreTokenResponse(
            accessToken = this
        )
}