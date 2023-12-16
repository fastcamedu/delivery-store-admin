package com.fastcampus.deliverystoreadmin.repository.token

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "refresh_tokens", schema = "food_delivery")
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    val refreshTokenId: Long = 0,

    @Column(name = "email")
    val email: String,

    @Column(name = "refresh_token")
    var refreshToken: String,
)