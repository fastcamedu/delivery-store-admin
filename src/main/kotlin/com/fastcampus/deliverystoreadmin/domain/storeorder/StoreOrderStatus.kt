package com.fastcampus.deliverystoreadmin.domain.storeorder

enum class StoreOrderStatus(description: String) {
    READY("주문 대기"),
    COOKING("조리 중"),
    DELIVERING("배달 중"),
    CANCEL("주문 취소"),
    COMPLETE("배달 완료")
}