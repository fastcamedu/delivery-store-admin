package com.fastcampus.deliverystoreadmin.repository.message

import com.fastcampus.deliverystoreadmin.repository.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "payment_messages", schema = "delivery_store")
class PaymentMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_message_id")
    val paymentMessageId: Long = 0L,

    @Column(name = "message")
    val message: String,
) : BaseEntity()