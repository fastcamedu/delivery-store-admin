package com.fastcampus.deliverystoreadmin.controller.settlement

import com.fastcampus.deliverystoreadmin.common.HttpConstants
import com.fastcampus.deliverystoreadmin.controller.settlement.dto.WithdrawRequest
import com.fastcampus.deliverystoreadmin.controller.settlement.dto.WithdrawResponse
import com.fastcampus.deliverystoreadmin.service.wallet.WalletService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SettlementApiController(
    private val walletService: WalletService,
) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }
    @PostMapping("/api/settlement/withdraw")
    fun withdraw(
        @RequestBody
        withdrawRequest: WithdrawRequest,
        @CookieValue(HttpConstants.COOKIE_NAME_STORE_ID) storeId: Long
    ): ResponseEntity<WithdrawResponse> {
        logger.info { ">>> 인출 요청: $storeId, $withdrawRequest" }

        val wallet = walletService.withdraw(storeId = storeId, withdrawAmount = withdrawRequest.amount)

        return ResponseEntity.ok(
            WithdrawResponse(
                storeId = storeId,
                balance = wallet.balance,
                message = "출금이 완료되었습니다."
            )
        )
    }
}