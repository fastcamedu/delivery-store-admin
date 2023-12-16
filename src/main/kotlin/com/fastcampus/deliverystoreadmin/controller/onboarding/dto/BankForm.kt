package com.fastcampus.deliverystoreadmin.controller.onboarding.dto

import com.fastcampus.deliverystoreadmin.domain.onboarding.BankCode
import org.apache.logging.log4j.util.Strings

data class BankForm(
    var bankCode: BankCode = BankCode.KB,
    var bankAccount: String = Strings.EMPTY,
    var bankAccountName: String = Strings.EMPTY,
    )
