package com.fastcampus.deliverystoreadmin.config

import com.fastcampus.deliverystoreadmin.repository.store.StoreRepository
import com.fastcampus.deliverystoreadmin.security.JwtProperties
import com.fastcampus.deliverystoreadmin.service.store.StoreDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class DeliveryStoreConfig {

    @Bean
    fun userDetailsService(
        storeRepository: StoreRepository,
    ): UserDetailsService =
        StoreDetailsService(storeRepository)

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(
        storeRepository: StoreRepository,
        passwordEncoder: PasswordEncoder
    ): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(storeRepository))
                it.setPasswordEncoder(passwordEncoder())
            }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager
}