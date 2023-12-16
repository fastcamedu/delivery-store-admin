package com.fastcampus.deliverystoreadmin.repository.wallehistory

import com.fastcampus.deliverystoreadmin.domain.wallet.WalletJobType
import com.fastcampus.deliverystoreadmin.repository.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "store_wallet_histories", schema = "delivery_store")
data class WalletHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_wallet_history_id")
    val storeWalletHistoryId: Long = 0L,

    @Column(name = "store_wallet_id")
    val storeWalletId: Long,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "wallet_job_type")
    val walletJobType: WalletJobType,

    @Column(name = "amount")
    val amount: BigDecimal,

    @Column(name = "balance")
    val balance: BigDecimal,
): BaseEntity()