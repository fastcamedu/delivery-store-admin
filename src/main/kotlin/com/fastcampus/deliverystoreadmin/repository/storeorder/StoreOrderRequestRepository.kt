package com.fastcampus.deliverystoreadmin.repository.storeorder

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreOrderRequestRepository: JpaRepository<StoreOrderRequest, Long> {
}