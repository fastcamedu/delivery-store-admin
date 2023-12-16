package com.fastcampus.deliverystoreadmin.repository.wallehistory

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletHistoryRepository: JpaRepository<WalletHistory, Long> {
}