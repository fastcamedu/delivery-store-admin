package com.fastcampus.deliverystoreadmin.service.storeorder

import com.fastcampus.deliverystoreadmin.repository.storeorder.StoreOrderRequestRepository
import org.springframework.stereotype.Service

@Service
class StoreOrderRequestService(
    private val storeOrderRequestRepository: StoreOrderRequestRepository,
) {
    fun countByStoreId(storeId: Long): Long {
        return storeOrderRequestRepository.count()
    }
}