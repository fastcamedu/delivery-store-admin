package com.fastcampus.deliverystoreadmin.consumer.payment

import com.fastcampus.deliverystoreadmin.domain.payment.Payment
import com.fastcampus.deliverystoreadmin.service.message.PaymentMessageService
import com.fastcampus.deliverystoreadmin.service.settlement.SettlementService
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class DeliveryPaymentMessageConsumer(
    private val settlementService: SettlementService,
    private val paymentMessageService: PaymentMessageService,
    private val objectMapper: ObjectMapper,
) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

//    @KafkaListener(
//        groupId = "delivery-payment-group",
//        topics = ["delivery-payment-complete"],
//    )
    fun receiveMessage(message: String) {
        logger.info { "Received message: $message" }

        val payment = objectMapper.readValue(message, Payment::class.java)
        validateSettlement(payment)

        // 메세지 원본 저장
        paymentMessageService.create(message)

        // 정산 데이터 저장
        this.settlementService.create(payment)
    }

    private fun validateSettlement(payment: Payment) {
        if (settlementService.existByPaymentId(payment.paymentId)) {
            throw DuplicateKeyException("이미 정산된 결제입니다. PaymentId = ${payment.paymentId}")
        }
    }
}