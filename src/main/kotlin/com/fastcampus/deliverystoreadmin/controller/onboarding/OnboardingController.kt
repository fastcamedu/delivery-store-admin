package com.fastcampus.deliverystoreadmin.controller.onboarding

import com.fastcampus.deliverystoreadmin.controller.onboarding.dto.BankForm
import com.fastcampus.deliverystoreadmin.controller.onboarding.dto.OnboardingStoreForm
import com.fastcampus.deliverystoreadmin.controller.onboarding.dto.StoreForm
import com.fastcampus.deliverystoreadmin.controller.onboarding.dto.StoreOwnerForm
import com.fastcampus.deliverystoreadmin.infrastructure.redis.RedisService
import com.fastcampus.deliverystoreadmin.service.signup.SignupService
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.util.*

@Controller
class OnboardingController(
    private val redisService: RedisService,
    private val objectMapper: ObjectMapper,
    private val signupService: SignupService,
) {
    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)

        private const val COOKIE_KEY_CLIENT_ID = "onboarding-client-id"
        private const val MINUTES_30 = 30 * 60
        private const val STORE_OWNER_FORM_KEY_POSTFIX = "store-owner"

        private const val ATTRIBUTE_KEY_STORE_OWNER_FORM = "storeOwnerForm"
        private const val ATTRIBUTE_KEY_STORE_FORM = "storeForm"
        private const val ATTRIBUTE_KEY_BANK_FORM = "bankForm"
        private const val ATTRIBUTE_KEY_ONBOARDING_STORE_FORM = "onboardingStoreForm"
    }

    @GetMapping("/onboarding/step/store-owner")
    fun storeOwnerForm(
        @CookieValue(COOKIE_KEY_CLIENT_ID) clientId: String?,
        request: HttpServletRequest,
        response: HttpServletResponse,
        model: Model
    ): String {
        logger.info { ">>> 사장님 정보 폼" }
        val clientIdCookie = request.cookies.firstOrNull { it.name == COOKIE_KEY_CLIENT_ID }
        if (clientIdCookie == null) {
            initClientIdToCookie(response)
            model.addAttribute(ATTRIBUTE_KEY_STORE_OWNER_FORM, StoreOwnerForm())
        } else {
            val clientId = clientIdCookie.value
            logger.info { ">>> 임시 저장 ClientID 읽기, clientId: $clientId" }

            val cachedOnboardingStoreForm = restoreOnboardingForm(clientId)
            logger.info { ">>> 임시 저장 ClientID 읽기, ${cachedOnboardingStoreForm.storeForm}, ${cachedOnboardingStoreForm.storeOwnerForm}, ${cachedOnboardingStoreForm.bankForm}" }

            model.addAttribute(ATTRIBUTE_KEY_STORE_OWNER_FORM, cachedOnboardingStoreForm.storeOwnerForm)
        }
        return "/onboarding/onboarding-store-owner"
    }

    @PostMapping("/onboarding/step/store-owner")
    fun storeOwner(
        @CookieValue(COOKIE_KEY_CLIENT_ID) clientId: String,
        @ModelAttribute storeOwnerForm: StoreOwnerForm,
        model: Model
    ): String {
        logger.info { ">>> 사장님 정보 폼 캐싱" }
        logger.info { ">>> ClientID: $clientId, storeOwnerForm: $storeOwnerForm" }

        val cachedOnboardingStoreForm = restoreOnboardingForm(clientId)
        cachedOnboardingStoreForm.storeOwnerForm = storeOwnerForm
        logger.info { ">>> 임시 저장 ClientID 읽기, ${cachedOnboardingStoreForm.storeForm}, ${cachedOnboardingStoreForm.storeOwnerForm}, ${cachedOnboardingStoreForm.bankForm}" }

        val onboardingStoreFormString = objectMapper.writeValueAsString(cachedOnboardingStoreForm)
        redisService.writeToRedis(clientId, onboardingStoreFormString)

        return "redirect:/onboarding/step/store"
    }

    @GetMapping("/onboarding/step/store")
    fun storeForm(
        @CookieValue(COOKIE_KEY_CLIENT_ID) clientId: String,
        request: HttpServletRequest,
        response: HttpServletResponse,
        model: Model
    ): String {
        logger.info { ">>> 매장 정보 폼" }

        val cachedOnboardingStoreForm = restoreOnboardingForm(clientId)
        logger.info { ">>> 임시 저장 ClientID 읽기, ${cachedOnboardingStoreForm.storeForm}, ${cachedOnboardingStoreForm.storeOwnerForm}, ${cachedOnboardingStoreForm.bankForm}" }

        model.addAttribute(ATTRIBUTE_KEY_STORE_FORM, cachedOnboardingStoreForm.storeForm)

        return "/onboarding/onboarding-store"
    }

    @PostMapping("/onboarding/step/store")
    fun storeForm(
        @CookieValue(COOKIE_KEY_CLIENT_ID) clientId: String,
        @ModelAttribute storeForm: StoreForm,
    ): String {
        logger.info { ">>> 매장 정보 폼 캐싱" }

        val cachedOnboardingStoreForm = restoreOnboardingForm(clientId)
        cachedOnboardingStoreForm.storeForm = storeForm
        logger.info { ">>> 임시 저장 ClientID 읽기, ${cachedOnboardingStoreForm.storeForm}, ${cachedOnboardingStoreForm.storeOwnerForm}, ${cachedOnboardingStoreForm.bankForm}" }

        val onboardingStoreFormString = objectMapper.writeValueAsString(cachedOnboardingStoreForm)
        redisService.writeToRedis(clientId, onboardingStoreFormString)

        return "redirect:/onboarding/step/bank"
    }

    @GetMapping("/onboarding/step/bank")
    fun bankForm(
        @CookieValue(COOKIE_KEY_CLIENT_ID) clientId: String,
        request: HttpServletRequest,
        response: HttpServletResponse,
        model: Model
    ): String {
        logger.info { ">>> 은행 정보 폼" }

        val cachedOnboardingStoreForm = restoreOnboardingForm(clientId)
        logger.info { ">>> 임시 저장 ClientID 읽기, ${cachedOnboardingStoreForm.storeForm}, ${cachedOnboardingStoreForm.storeOwnerForm}, ${cachedOnboardingStoreForm.bankForm}" }

        model.addAttribute(ATTRIBUTE_KEY_BANK_FORM, cachedOnboardingStoreForm.bankForm)
        return "/onboarding/onboarding-bank"
    }

    @PostMapping("/onboarding/step/bank")
    fun bank(
        @CookieValue(COOKIE_KEY_CLIENT_ID) clientId: String,
        @ModelAttribute bankForm: BankForm,
    ): String {
        logger.info { ">>> 은행 정보 캐싱" }

        val cachedOnboardingStoreForm = restoreOnboardingForm(clientId)
        cachedOnboardingStoreForm.bankForm = bankForm
        logger.info { ">>> 임시 저장 ClientID 읽기, ${cachedOnboardingStoreForm.storeForm}, ${cachedOnboardingStoreForm.storeOwnerForm}, ${cachedOnboardingStoreForm.bankForm}" }

        val onboardingStoreFormString = objectMapper.writeValueAsString(cachedOnboardingStoreForm)
        redisService.writeToRedis(clientId, onboardingStoreFormString)

        return "redirect:/onboarding/step/confirm"
    }

    @GetMapping("/onboarding/step/confirm")
    fun confirm(
        @CookieValue(COOKIE_KEY_CLIENT_ID) clientId: String,
        request: HttpServletRequest,
        response: HttpServletResponse,
        model: Model
    ): String {
        logger.info { ">>> 최종 확인 폼" }

        val cachedOnboardingStoreForm = restoreOnboardingForm(clientId)
        logger.info { ">>> 임시 저장 ClientID 읽기, ${cachedOnboardingStoreForm.storeForm}, ${cachedOnboardingStoreForm.storeOwnerForm}, ${cachedOnboardingStoreForm.bankForm}" }

        model.addAttribute(ATTRIBUTE_KEY_STORE_FORM, cachedOnboardingStoreForm.storeForm)
        model.addAttribute(ATTRIBUTE_KEY_BANK_FORM, cachedOnboardingStoreForm.bankForm)
        model.addAttribute(ATTRIBUTE_KEY_STORE_OWNER_FORM, cachedOnboardingStoreForm.storeOwnerForm)

        return "/onboarding/onboarding-confirm"
    }

    @PostMapping("/onboarding/step/signup")
    fun signup(
        @CookieValue(COOKIE_KEY_CLIENT_ID) clientId: String,
        model: Model
    ): String {
        logger.info { ">>> 상점 온보딩 가입 실행" }
        val cachedOnboardingStoreForm = restoreOnboardingForm(clientId)

        logger.info { ">>> 임시 저장 ClientID 읽기, ${cachedOnboardingStoreForm.storeForm}, ${cachedOnboardingStoreForm.storeOwnerForm}, ${cachedOnboardingStoreForm.bankForm}" }
        val store = signupService.signup(cachedOnboardingStoreForm)

        model.addAttribute("store", store)

        return "redirect:/onboarding/step/success"
    }

    @GetMapping("/onboarding/step/success")
    fun success(): String {
        logger.info { ">>> 가입 성공" }
        return "/onboarding/onboarding-success"
    }

    private fun restoreOnboardingForm(clientId: String): OnboardingStoreForm {
        val cachedOnboardingStoreForm = redisService.readFromRedis(clientId)
        if (cachedOnboardingStoreForm == null) {
            logger.info { ">>> 임시 저장 ClientID 없음" }
            return OnboardingStoreForm(
                storeOwnerForm = StoreOwnerForm(),
                storeForm = StoreForm(),
                bankForm = BankForm(),
            )
        }
        return objectMapper.readValue(cachedOnboardingStoreForm, OnboardingStoreForm::class.java)
    }

    private fun initClientIdToCookie(response: HttpServletResponse) {
        val cookieClientIdValue = "${COOKIE_KEY_CLIENT_ID}_${UUID.randomUUID()}"
        val clientIdCookie = Cookie(COOKIE_KEY_CLIENT_ID, cookieClientIdValue)
        clientIdCookie.maxAge = MINUTES_30;
        clientIdCookie.path = "/"; // 모든 경로에서 접근 가능 하도록 설정

        logger.info { ">>> 쿠키에 Client ID 추가: $cookieClientIdValue" }
        response.addCookie(clientIdCookie);
    }
}