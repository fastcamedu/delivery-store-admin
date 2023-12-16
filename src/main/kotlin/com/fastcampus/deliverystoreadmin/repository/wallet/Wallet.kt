package com.fastcampus.deliverystoreadmin.repository.wallet

import com.fastcampus.deliverystoreadmin.repository.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "store_wallets", schema = "delivery_store")
data class Wallet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_wallet_id")
    val id: Long = 0L,

    @Column(name = "store_id")
    val storeId: Long,

    @Column(name = "balance")
    var balance: BigDecimal,
): BaseEntity()