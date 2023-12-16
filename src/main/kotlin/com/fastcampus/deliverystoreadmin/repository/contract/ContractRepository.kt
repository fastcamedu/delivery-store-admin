package com.fastcampus.deliverystoreadmin.repository.contract

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ContractRepository : JpaRepository<Contract, Long> {
    @Query(
        value = """
            SELECT c 
            FROM Contract c 
            WHERE c.storeId = :storeId AND :baseDate BETWEEN c.startDate AND c.endDate AND c.isDeleted = false
        """
    )
    fun findAllContracts(@Param("storeId") storeId: Long, @Param("baseDate") baseDate: LocalDate): List<Contract>
}