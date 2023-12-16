package com.fastcampus.deliverystoreadmin.repository.settlements

import com.fastcampus.deliverystoreadmin.domain.payment.Payment
import com.fastcampus.deliverystoreadmin.repository.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "store_settlements", schema = "delivery_store")
data class Settlement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_settlement_id")
    val settlementId: Long = 0L,

    @Column(name = "store_id")
    val storeId: Long,

    @Column(name = "order_id")
    val orderId: Long,

    @Column(name = "payment_id")
    val paymentId: Long,

    @Column(name = "order_amount")
    val orderAmount: BigDecimal,

    @Column(name = "discount_amount")
    val discountAmount: BigDecimal,

    @Column(name = "delivery_fee")
    val deliveryFee: BigDecimal,

    @Column(name = "promotion_fee")
    val promotionFee: BigDecimal,

    @Column(name = "fee_rate")
    val feeRate: BigDecimal,

    @Column(name = "fee_amount")
    val feeAmount: BigDecimal,

    @Column(name = "payment_amount")
    val paymentAmount: BigDecimal,

    @Column(name = "settlement_amount")
    val settlementAmount: BigDecimal,
): BaseEntity() {
    companion object {
        fun from(payment: Payment, feeRate: BigDecimal, roleName: String): Settlement {
            val feeAmount = payment.payAmount.multiply(feeRate)
            val settlement = Settlement(
                storeId = payment.storeId,
                orderId = payment.orderId,
                paymentId = payment.paymentId,
                orderAmount = payment.orderAmount,
                discountAmount = payment.discountAmount,
                deliveryFee = payment.deliveryFee,
                promotionFee = payment.promotionFee,
                feeRate = feeRate,
                feeAmount = feeAmount,
                paymentAmount = payment.payAmount,
                settlementAmount = payment.payAmount.minus(feeAmount)
            )
            settlement.createdBy = roleName
            settlement.updatedBy = roleName
            return settlement
        }
    }
}
