package com.fastcampus.deliverystoreadmin.controller.onboarding.dto

import org.apache.logging.log4j.util.Strings
import java.math.BigDecimal

data class StoreForm(
    var storeName: String = Strings.EMPTY,
    var storePhone: String = Strings.EMPTY,
    var managerName: String = Strings.EMPTY,
    var managerPhone: String = Strings.EMPTY,
    var storeAddress: String = Strings.EMPTY,
    var storeDetailAddress: String = Strings.EMPTY,
    var businessNumber: String = Strings.EMPTY,
    var deliveryTime: String = Strings.EMPTY,
    var storeMainImageUrl: String = Strings.EMPTY,
    var description: String = Strings.EMPTY,
    var deliveryFee: BigDecimal = BigDecimal(3_000),
    var minimumOrderPrice: BigDecimal = BigDecimal(10_000),
) {

}