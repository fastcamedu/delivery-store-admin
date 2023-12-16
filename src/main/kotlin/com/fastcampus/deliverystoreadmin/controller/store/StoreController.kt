package com.fastcampus.deliverystoreadmin.controller.store

import com.fastcampus.deliverystoreadmin.common.HttpConstants
import com.fastcampus.deliverystoreadmin.controller.store.dto.StoreFormDTO
import com.fastcampus.deliverystoreadmin.service.store.StoreService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class StoreController(
    private val storeService: StoreService,
) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @GetMapping("/store/detail")
    fun detailPage(
        @CookieValue(HttpConstants.COOKIE_NAME_STORE_ID) storeId: Long,
        model: Model
    ): String {
        SecurityContextHolder.getContext().authentication?.let {
            logger.info { ">>> principal: ${it.principal}" }
            model.addAttribute("email", it.name)
        }

        storeService.findById(storeId = storeId).let {
            if (it.isEmpty) {
                logger.error { ">>> 매장 정보가 없습니다." }
                return "redirect:/dashboard"
            }
            val store = it.get()
            val storeFormDTO = StoreFormDTO.of(store)
            model.addAttribute("storeFormDTO", storeFormDTO)
        }

        return "/store/detail"
    }

    @PostMapping("/store/detail")
    fun detail(
        @CookieValue(HttpConstants.COOKIE_NAME_STORE_ID) storeId: Long,
        @ModelAttribute storeFormDTO: StoreFormDTO
    ): String {
        logger.info { ">>> storeFormDTO: $storeFormDTO" }

        storeService.update(storeId = storeId, storeFormDTO = storeFormDTO)

        return "redirect:/store/detail"
    }
}