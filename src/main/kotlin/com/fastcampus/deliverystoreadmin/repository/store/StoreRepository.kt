package com.fastcampus.deliverystoreadmin.repository.store

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
    fun findByEmail(email: String): Optional<Store>
}