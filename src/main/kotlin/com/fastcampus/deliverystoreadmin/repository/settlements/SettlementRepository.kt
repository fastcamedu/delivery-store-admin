package com.fastcampus.deliverystoreadmin.repository.settlements

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SettlementRepository : JpaRepository<Settlement, Long>{
    fun existsByPaymentId(paymentId: Long): Boolean
    fun findAllByStoreIdOrderByCreatedAtDesc(storeId: Long): List<Settlement>
    fun countByStoreId(storeId: Long): Long
}