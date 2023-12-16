package com.fastcampus.deliverystoreadmin.repository.store

import com.fastcampus.deliverystoreadmin.domain.onboarding.BankCode
import com.fastcampus.deliverystoreadmin.domain.store.StoreStatus
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
@Table(name = "stores", catalog = "delivery_store")
class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    val storeId: Long = 0L,

    @Column(name = "store_name", nullable = false)
    val storeName: String,

    @Column(name = "store_address", nullable = false)
    val storeAddress: String,

    @Column(name = "business_number", nullable = false)
    val businessNumber: String,

    @Column(name = "manager_name", nullable = false)
    val managerName: String,

    @Column(name = "manager_phone", nullable = false)
    val managerPhone: String,

    @Column(name = "store_phone", nullable = false)
    var storePhone: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "phone", nullable = false)
    val phone: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "bank_code", nullable = false)
    val bankCode: BankCode,

    @Column(name = "bank_account", nullable = false)
    val bankAccount: String,

    @Column(name = "bank_account_name", nullable = false)
    val bankAccountName: String,

    @Column(name = "main_image_url", nullable = false)
    val storeMainImageUrl: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "store_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val storeStatus: StoreStatus,

    @Column(name = "delivery_fee", nullable = false)
    var deliveryFee: BigDecimal,

    @Column(name = "delivery_time", nullable = false)
    var deliveryTime: String,

    @Column(name = "review_grade", nullable = false)
    val reviewGrade: Int,

    @Column(name = "minimum_order_price", nullable = false)
    var minimumOrderPrice: BigDecimal,
): BaseEntity()
