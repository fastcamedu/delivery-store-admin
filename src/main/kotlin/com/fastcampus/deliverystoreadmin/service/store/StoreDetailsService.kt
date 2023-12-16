package com.fastcampus.deliverystoreadmin.service.store

import com.fastcampus.deliverystoreadmin.domain.store.StoreRole
import com.fastcampus.deliverystoreadmin.exception.NotFoundException
import com.fastcampus.deliverystoreadmin.repository.store.StoreRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class StoreDetailsService(
    private val storeRepository: StoreRepository,
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val storeOptional = storeRepository.findByEmail(username)
        if (storeOptional.isEmpty) {
            throw NotFoundException("상점 정보를 찾을 수 없습니다.")
        }
        val store = storeOptional.get()
        return User.builder()
            .username(store.email)
            .password(store.password)
            .roles(StoreRole.OWNER.name)
            .build()
    }
}