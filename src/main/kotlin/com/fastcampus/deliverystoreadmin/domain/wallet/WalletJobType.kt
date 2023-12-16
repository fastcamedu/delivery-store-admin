package com.fastcampus.deliverystoreadmin.domain.wallet

enum class WalletJobType(description: String) {
    WITHDRAW("출금"),
    DEPOSIT("입금"),
    SETTLEMENT("정산"),
    REFUND("환불")
}