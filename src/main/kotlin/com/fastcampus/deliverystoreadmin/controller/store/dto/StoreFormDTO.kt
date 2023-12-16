package com.fastcampus.deliverystoreadmin.controller.store.dto

import com.fastcampus.deliverystoreadmin.repository.store.Store
import java.math.BigDecimal

data class StoreFormDTO(
    val storePhone: String,
    val deliveryFee: BigDecimal,
    val deliveryTime: String,
    val minimumOrderPrice: String,
) {
    companion object {
        fun of(store: Store): StoreFormDTO {
            return StoreFormDTO(
                storePhone = store.storePhone,
                deliveryFee = store.deliveryFee,
                deliveryTime = store.deliveryTime,
                minimumOrderPrice = store.minimumOrderPrice.toPlainString(),
            )
        }
    }
}
