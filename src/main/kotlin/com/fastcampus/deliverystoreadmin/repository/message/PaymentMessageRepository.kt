package com.fastcampus.deliverystoreadmin.repository.message

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentMessageRepository : JpaRepository<PaymentMessage, Long>