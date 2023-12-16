package com.fastcampus.deliverystoreadmin.service.wallethistory

import com.fastcampus.deliverystoreadmin.repository.wallehistory.WalletHistoryRepository
import org.springframework.stereotype.Service

@Service
class WalletHistoryService(
    private val walletHistoryRepository: WalletHistoryRepository,
) {
}