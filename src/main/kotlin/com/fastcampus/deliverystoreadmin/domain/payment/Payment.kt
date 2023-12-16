package com.fastcampus.deliverystoreadmin.domain.payment

import java.math.BigDecimal

data class Payment(
    val storeId: Long,
    val orderId: Long,
    val paymentId: Long,
    val orderAmount: BigDecimal,
    val discountAmount: BigDecimal,
    val deliveryFee: BigDecimal,
    val promotionFee: BigDecimal,
    val payAmount: BigDecimal,
)