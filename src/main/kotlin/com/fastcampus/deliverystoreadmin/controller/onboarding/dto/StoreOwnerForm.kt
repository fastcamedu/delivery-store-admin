package com.fastcampus.deliverystoreadmin.controller.onboarding.dto

import org.apache.logging.log4j.util.Strings

data class StoreOwnerForm(
    var email: String = Strings.EMPTY,
    var name: String =  Strings.EMPTY,
    var phone: String =  Strings.EMPTY,
    var password1: String =  Strings.EMPTY,
    var password2: String =  Strings.EMPTY,
)