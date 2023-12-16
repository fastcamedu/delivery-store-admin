package com.fastcampus.deliverystoreadmin.service.message

import com.fastcampus.deliverystoreadmin.repository.message.PaymentMessage
import com.fastcampus.deliverystoreadmin.repository.message.PaymentMessageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class PaymentMessageService(
    private val paymentMessageRepository: PaymentMessageRepository,
) {
    @Value("\${server.role-name}")
    private lateinit var roleName: String

    fun create(paymentMessage: String): PaymentMessage {
        val paymentMessage = PaymentMessage(
            message = paymentMessage,
        )
        paymentMessage.createdBy = roleName
        paymentMessage.updatedBy = roleName
        return this.paymentMessageRepository.save(paymentMessage)
    }

    fun count(): Long = this.paymentMessageRepository.count()
}