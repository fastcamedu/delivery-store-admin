package com.fastcampus.deliverystoreadmin.repository.contract

import com.fastcampus.deliverystoreadmin.domain.contract.ContractType
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
import java.time.LocalDate

@Entity
@Table(name = "contracts")
data class Contract(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    val contractId: Long,

    @Column(name = "store_id")
    val storeId: Long,

    @Column(name = "start_date")
    val startDate: LocalDate,

    @Column(name = "end_date")
    val endDate: LocalDate,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "contract_type")
    val contractType: ContractType,

    @Column(name = "fee_rate")
    val feeRate: BigDecimal,
): BaseEntity()