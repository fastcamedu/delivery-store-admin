package com.fastcampus.deliverystoreadmin.domain.store

enum class StoreStatus(description: String) {
    INIT("신규매장 준비중"),
    READY("오픈 준비중"),
    SALE("판매중"),
    CLOSE("판매종료"),
}