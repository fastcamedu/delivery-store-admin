package com.fastcampus.deliverystoreadmin.repository.storeorder

import com.fastcampus.deliverystoreadmin.domain.storeorder.StoreOrderStatus
import com.fastcampus.deliverystoreadmin.repository.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "store_order_requests", schema = "delivery_store")
class StoreOrderRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_order_request_id")
    val storeOrderRequestId: Long,

    @Column(name = "order_id")
    val orderId: Long,

    @Column(name = "order_shorten_id")
    val orderShortenId: String,

    @Column(name = "order_uuid")
    val order_uuid: String,

    @Column(name = "store_id")
    val storeId: Long,

    @Column(name = "customer_id")
    val customerId: Long,

    @Column(name = "store_order_status")
    @Enumerated(value = EnumType.STRING)
    val storeOrderStatus: StoreOrderStatus,

    @Column(name = "cooking_minutes")
    val cookingMinutes: Int,
): BaseEntity()