package com.fastcampus.deliverystoreadmin.repository.token

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
    fun findByEmail(email: String): Optional<RefreshToken>
}