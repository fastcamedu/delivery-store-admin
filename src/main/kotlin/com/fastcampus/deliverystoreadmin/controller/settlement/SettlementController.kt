package com.fastcampus.deliverystoreadmin.controller.settlement

import com.fastcampus.deliverystoreadmin.common.HttpConstants
import com.fastcampus.deliverystoreadmin.service.settlement.SettlementService
import com.fastcampus.deliverystoreadmin.service.wallet.WalletService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SettlementController(
    private val settlementService: SettlementService,
    private val walletService: WalletService,
) {
    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @GetMapping("/store/settlements")
    fun list(
        @CookieValue(HttpConstants.COOKIE_NAME_STORE_ID) storeId: Long,
        model: Model
    ): String {
        logger.info { ">>> 정산 리스트 요청: $storeId" }
        val settlements = settlementService.findAll(storeId = storeId)
        model.addAttribute("settlements", settlements)

        val balance = walletService.getBalance(storeId = storeId)
        model.addAttribute("balance", balance)

        return "/settlement/list"
    }
}