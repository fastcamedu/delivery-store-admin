package com.fastcampus.deliverystoreadmin.controller.settlement.dto

import java.math.BigDecimal

data class WithdrawResponse(
    val storeId: Long,
    val balance: BigDecimal,
    val message: String,
)