package com.fastcampus.deliverystoreadmin.service.wallet

import com.fastcampus.deliverystoreadmin.domain.wallet.WalletJobType
import com.fastcampus.deliverystoreadmin.exception.NotFoundException
import com.fastcampus.deliverystoreadmin.repository.wallehistory.WalletHistory
import com.fastcampus.deliverystoreadmin.repository.wallehistory.WalletHistoryRepository
import com.fastcampus.deliverystoreadmin.repository.wallet.Wallet
import com.fastcampus.deliverystoreadmin.repository.wallet.WalletRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional(readOnly = false)
class WalletService(
    private val walletRepository: WalletRepository,
    private val walletHistoryRepository: WalletHistoryRepository
) {
    companion object {
        private val logger = KotlinLogging.logger {  }
    }

    fun getBalance(storeId: Long): BigDecimal {
        logger.info { ">>> 지갑 잔액 조회 요청: $storeId" }

        val walletOptional = walletRepository.findByStoreId(storeId)
        if (walletOptional.isEmpty) {
            logger.info { ">>> 지갑 생성: $storeId" }
            val wallet = Wallet(storeId = storeId, balance = BigDecimal.ZERO)
            walletRepository.save(wallet)
            return BigDecimal.ZERO
        }

        return walletOptional.get().balance
    }

    fun withdraw(storeId: Long, withdrawAmount: BigDecimal): Wallet {
        logger.info { ">>> 지갑 출금 요청: $storeId, $withdrawAmount" }

        val walletOptional = walletRepository.findByStoreId(storeId)
        if (walletOptional.isEmpty) {
            logger.error { ">>> 지갑이 존재하지 않습니다. $storeId" }
            throw NotFoundException("지갑이 존재하지 않습니다.")
        }

        val wallet = walletOptional.get()
        val deposit = wallet.balance
        if (deposit < withdrawAmount) {
            logger.error { ">>> 잔액이 부족합니다. $storeId, $deposit, $withdrawAmount" }
            throw RuntimeException("잔액이 부족합니다.")
        }
        wallet.balance = deposit.minus(withdrawAmount)

        val walletHistory = WalletHistory(
            storeWalletId = wallet.id,
            walletJobType = WalletJobType.WITHDRAW,
            amount = withdrawAmount,
            balance = wallet.balance,
        )
        walletHistoryRepository.save(walletHistory)
        return walletRepository.save(wallet)
    }
}