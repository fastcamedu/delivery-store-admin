package com.fastcampus.deliverystoreadmin.service.store

import com.fastcampus.deliverystoreadmin.controller.store.dto.StoreFormDTO
import com.fastcampus.deliverystoreadmin.repository.store.Store
import com.fastcampus.deliverystoreadmin.repository.store.StoreRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class StoreService(
    private val storeRepository: StoreRepository,
) {
    fun findByEmail(email: String): Optional<Store> {
        return storeRepository.findByEmail(email)
    }

    fun findById(storeId: Long): Optional<Store> {
        return storeRepository.findById(storeId)
    }

    fun update(storeId: Long, storeFormDTO: StoreFormDTO): Store {
        val storeOptional = storeRepository.findById(storeId)
        if (storeOptional.isEmpty) {
            throw RuntimeException("매장 정보가 없습니다.")
        }
        val store = storeOptional.get()
        store.storePhone = storeFormDTO.storePhone
        store.deliveryFee = storeFormDTO.deliveryFee
        store.deliveryTime = storeFormDTO.deliveryTime
        store.minimumOrderPrice = BigDecimal(storeFormDTO.minimumOrderPrice)

        return storeRepository.save(store)
    }

}