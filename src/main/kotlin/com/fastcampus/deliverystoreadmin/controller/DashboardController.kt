package com.fastcampus.deliverystoreadmin.controller

import com.fastcampus.deliverystoreadmin.common.HttpConstants
import com.fastcampus.deliverystoreadmin.service.message.PaymentMessageService
import com.fastcampus.deliverystoreadmin.service.settlement.SettlementService
import com.fastcampus.deliverystoreadmin.service.storeorder.StoreOrderRequestService
import com.fastcampus.deliverystoreadmin.service.wallet.WalletService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DashboardController(
    private val walletService: WalletService,
    private val settlementService: SettlementService,
    private val paymentMessageService: PaymentMessageService,
    private val storeOrderRequestService: StoreOrderRequestService,
) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @GetMapping("/dashboard")
    fun dashboard(
        @CookieValue(HttpConstants.COOKIE_NAME_STORE_ID) storeId: Long,
        model: Model
    ): String {
        logger.info { ">>> dashboard" }

        SecurityContextHolder.getContext().authentication?.let {
            logger.info { ">>> principal: ${it.principal}" }
            model.addAttribute("email", it.name)
        }

        // 지갑 금액
        walletService.getBalance(storeId).let {
            model.addAttribute("balance", it)
        }

        // 결제 메세지
        paymentMessageService.count().let {
            model.addAttribute("paymentMessageCount", it)
        }

        // 정산 개수
        settlementService.countByStoreId(storeId).let {
            model.addAttribute("settlementCount", it)
        }

        // 상점에 들어온 주문 수
        storeOrderRequestService.countByStoreId(storeId).let {
            model.addAttribute("storeOrderRequestCount", it)
        }

        return "/dashboard/dashboard"
    }
}