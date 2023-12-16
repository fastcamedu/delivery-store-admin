package com.fastcampus.deliverystoreadmin.service.settlement

import com.fastcampus.deliverystoreadmin.domain.payment.Payment
import com.fastcampus.deliverystoreadmin.repository.settlements.Settlement
import com.fastcampus.deliverystoreadmin.repository.settlements.SettlementRepository
import com.fastcampus.deliverystoreadmin.service.contract.ContractService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SettlementService(
    private val settlementRepository: SettlementRepository,
    private val contractService: ContractService,
) {
    @Value("\${server.role-name}")
    private lateinit var roleName: String

    fun findAll(storeId: Long): List<Settlement> {
        SecurityContextHolder.getContext().authentication.name
        return settlementRepository.findAllByStoreIdOrderByCreatedAtDesc(storeId = storeId)
    }

    fun create(payment: Payment): Settlement {
        val fee = contractService.getAvailableFeeRate(payment.storeId, LocalDate.now())
        val settlement = Settlement.from(payment, fee.feeRate, roleName)
        return this.settlementRepository.save(settlement)
    }

    fun existByPaymentId(paymentId: Long): Boolean {
        return settlementRepository.existsByPaymentId(paymentId)
    }

    fun countByStoreId(storeId: Long): Long {
        return settlementRepository.countByStoreId(storeId)
    }
}