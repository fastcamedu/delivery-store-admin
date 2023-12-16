package com.fastcampus.deliverystoreadmin.repository.wallet

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WalletRepository: JpaRepository<Wallet, Long> {
    fun findByStoreId(storeId: Long): Optional<Wallet>
}