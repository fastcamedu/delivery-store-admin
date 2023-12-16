package com.fastcampus.deliverystoreadmin.service.auth


import com.fastcampus.deliverystoreadmin.controller.login.dto.LoginRequest
import com.fastcampus.deliverystoreadmin.controller.login.dto.LoginResponse
import com.fastcampus.deliverystoreadmin.repository.token.RefreshToken
import com.fastcampus.deliverystoreadmin.repository.token.RefreshTokenRepository
import com.fastcampus.deliverystoreadmin.security.JwtProperties
import com.fastcampus.deliverystoreadmin.service.store.StoreDetailsService
import com.fastcampus.deliverystoreadmin.service.store.StoreService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val storeService: StoreService,
    private val storeDetailsService: StoreDetailsService,
    private val deliveryStoreTokenService: DeliveryStoreTokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    fun authentication(loginRequest: LoginRequest): LoginResponse {
        val authentication = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.email,
                loginRequest.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication

        val storeOptional = storeService.findByEmail(loginRequest.email)
        if (storeOptional.isEmpty) {
            error("상점 정보가 없습니다.")
        }

        val store = storeOptional.get()
        val accessToken = createAccessToken(store.email)
        val refreshToken = createRefreshToken(store.email)

        val refreshTokenOptional = refreshTokenRepository.findByEmail(store.email)
        if (refreshTokenOptional.isEmpty) {
            refreshTokenRepository.save(RefreshToken(email = store.email, refreshToken = refreshToken))
        } else {
            val savedRefreshToken = refreshTokenOptional.get()
            savedRefreshToken.refreshToken = refreshToken
            refreshTokenRepository.save(savedRefreshToken)
        }

        return LoginResponse(
            storeId = store.storeId,
            email = loginRequest.email,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun refreshAccessToken(refreshToken: String): String? {
        val extractedEmail = deliveryStoreTokenService.extractEmail(refreshToken)

        return extractedEmail?.let { email ->
            val currentUserDetails = storeDetailsService.loadUserByUsername(email)
            val refreshTokenOptional = refreshTokenRepository.findByEmail(email)
            if (!deliveryStoreTokenService.isExpired(refreshToken) && refreshTokenOptional.get().email == currentUserDetails.username) {
                createAccessToken(currentUserDetails.username)
            } else {
                null
            }
        }
    }

    private fun createAccessToken(email: String) = deliveryStoreTokenService.generate(
        email = email,
        expirationDate = getAccessTokenExpiration()
    )

    private fun createRefreshToken(email: String) = deliveryStoreTokenService.generate(
        email = email,
        expirationDate = getRefreshTokenExpiration()
    )

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}