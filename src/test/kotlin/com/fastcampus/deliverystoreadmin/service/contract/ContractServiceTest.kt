package com.fastcampus.deliverystoreadmin.service.contract

import com.fastcampus.deliverystoreadmin.domain.contract.ContractType
import com.fastcampus.deliverystoreadmin.exception.NotFoundAvailableContract
import com.fastcampus.deliverystoreadmin.repository.contract.Contract
import com.fastcampus.deliverystoreadmin.repository.contract.ContractRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.time.LocalDate
import java.time.OffsetDateTime
import kotlin.random.Random

@ExtendWith(MockitoExtension::class)
class ContractServiceTest {

    @Mock
    private lateinit var contractRepository: ContractRepository

    @InjectMocks
    private lateinit var contractService: ContractService

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `일반 계약만 있으면 일반 계약 수수료를 적용한다`() {
        // given
        val testStoreId = 1L
        val baseDate = LocalDate.now()
        val baseFeeRate = BigDecimal(3)
        val contract = createContract(
            storeId = testStoreId,
            contractType = ContractType.BASIC,
            startDate = baseDate.minusMonths(1),
            endDate = baseDate.plusMonths(1),
            feeRate = baseFeeRate,
        )
        `when`(contractRepository.findAllContracts(
            storeId = testStoreId, baseDate = baseDate
        )).thenReturn(listOf(contract))

        // when
        val fee = contractService.getAvailableFeeRate(storeId = testStoreId, baseDate = baseDate)

        // then
        assertThat(fee).isNotNull
        assertThat(fee.feeRate).isEqualTo(baseFeeRate)
    }

    @Test
    fun `프로모션 계약만 있으면 프로모션 계약 수수료를 적용한다`() {
        // given
        val testStoreId = 1L
        val baseDate = LocalDate.now()
        val promotionFeeRate = BigDecimal(1)
        val contract = createContract(
            storeId = testStoreId,
            contractType = ContractType.PROMOTION,
            startDate = baseDate.minusMonths(1),
            endDate = baseDate.plusMonths(1),
            feeRate = promotionFeeRate,
        )
        `when`(contractRepository.findAllContracts(
            storeId = testStoreId, baseDate = baseDate
        )).thenReturn(listOf(contract))

        // when
        val fee = contractService.getAvailableFeeRate(storeId = testStoreId, baseDate = baseDate)

        // then
        assertThat(fee).isNotNull
        assertThat(fee.feeRate).isEqualTo(promotionFeeRate)
    }

    @Test
    fun `일반 계약과 프로모션 계약의 기간이 겹치면 프로모션 계약 수수료를 적용한다`() {
        // given
        val testStoreId = 1L
        val baseDate = LocalDate.now()
        val basicFeeRate = BigDecimal(3)
        val promotionFeeRate = BigDecimal(1)
        val basicContract = createContract(
            storeId = testStoreId,
            contractType = ContractType.BASIC,
            startDate = baseDate.minusMonths(1),
            endDate = baseDate.plusMonths(1),
            feeRate = basicFeeRate,
        )
        val promotionContract = createContract(
            storeId = testStoreId,
            contractType = ContractType.PROMOTION,
            startDate = baseDate.minusMonths(1),
            endDate = baseDate.plusMonths(1),
            feeRate = promotionFeeRate,
        )
        `when`(contractRepository.findAllContracts(
            storeId = testStoreId, baseDate = baseDate
        )).thenReturn(listOf(basicContract, promotionContract))

        // when
        val fee = contractService.getAvailableFeeRate(storeId = testStoreId, baseDate = baseDate)

        // then
        assertThat(fee).isNotNull
        assertThat(fee.feeRate).isEqualTo(promotionFeeRate)
    }

    @Test
    fun `계약 정보가 없으면 예외가 발생한다`() {
        // given
        val testStoreId = 1L
        val baseDate = LocalDate.now()
        `when`(contractRepository.findAllContracts(
            storeId = testStoreId, baseDate = baseDate
        )).thenReturn(emptyList())

        // when
        assertThatThrownBy {
            contractService.getAvailableFeeRate(storeId = testStoreId, baseDate = baseDate)
        }.isInstanceOf(NotFoundAvailableContract::class.java)
    }

    private fun createContract(
        storeId: Long,
        contractType: ContractType,
        feeRate: BigDecimal,
        startDate: LocalDate,
        endDate: LocalDate
    ): Contract {
        return Contract(
            contractId = Random.nextLong(),
            storeId = storeId,
            startDate = startDate,
            endDate = endDate,
            contractType = contractType,
            feeRate = feeRate,
            isDeleted = false,
            createdAt = OffsetDateTime.now(),
            updatedAt = OffsetDateTime.now(),
            createdBy = "tester",
            updatedBy = "tester",
        )
    }
}