package com.fastcampus.deliverystoreadmin.service.contract

import com.fastcampus.deliverystoreadmin.domain.contract.ContractType
import com.fastcampus.deliverystoreadmin.domain.fee.Fee
import com.fastcampus.deliverystoreadmin.exception.NotFoundAvailableContract
import com.fastcampus.deliverystoreadmin.repository.contract.ContractRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ContractService(
    private val contractRepository: ContractRepository
) {
    fun getAvailableFeeRate(storeId: Long, baseDate: LocalDate): Fee {
        val contracts =
            this.contractRepository.findAllContracts(
                storeId = storeId, baseDate = baseDate
            )
        val promotionContract = contracts.firstOrNull { it.contractType == ContractType.PROMOTION }
        if (promotionContract != null) {
            return Fee(feeRate = promotionContract.feeRate)
        }

        val basicContract = contracts.firstOrNull { it.contractType == ContractType.BASIC }
            ?: throw NotFoundAvailableContract("계약 정보가 없습니다.")
        return Fee(feeRate = basicContract.feeRate)
    }
}