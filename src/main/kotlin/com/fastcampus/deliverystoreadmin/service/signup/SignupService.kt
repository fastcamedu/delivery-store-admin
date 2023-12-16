package com.fastcampus.deliverystoreadmin.service.signup

import com.fastcampus.deliverystoreadmin.controller.onboarding.dto.OnboardingStoreForm
import com.fastcampus.deliverystoreadmin.domain.review.ReviewGrade
import com.fastcampus.deliverystoreadmin.domain.store.StoreStatus
import com.fastcampus.deliverystoreadmin.repository.store.Store
import com.fastcampus.deliverystoreadmin.repository.store.StoreRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignupService(
    private val storeRepository: StoreRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @Value("\${server.role-name}")
    private lateinit var roleName: String

    fun signup(onboardingStoreForm: OnboardingStoreForm): Store {
        val store = Store(
            storeName = onboardingStoreForm.storeForm.storeName,
            storeAddress = onboardingStoreForm.storeForm.storeAddress,
            storePhone = onboardingStoreForm.storeForm.storePhone,
            businessNumber = onboardingStoreForm.storeForm.businessNumber,
            managerName = onboardingStoreForm.storeForm.managerName,
            managerPhone = onboardingStoreForm.storeForm.managerPhone,
            email = onboardingStoreForm.storeOwnerForm.email,
            name = onboardingStoreForm.storeOwnerForm.name,
            phone = onboardingStoreForm.storeOwnerForm.phone,
            password = passwordEncoder.encode(onboardingStoreForm.storeOwnerForm.password1),
            bankCode = onboardingStoreForm.bankForm.bankCode,
            bankAccount = onboardingStoreForm.bankForm.bankAccount,
            bankAccountName = onboardingStoreForm.bankForm.bankAccountName,
            deliveryTime = onboardingStoreForm.storeForm.deliveryTime,
            storeMainImageUrl = onboardingStoreForm.storeForm.storeMainImageUrl,
            description = onboardingStoreForm.storeForm.description,
            deliveryFee = onboardingStoreForm.storeForm.deliveryFee,
            reviewGrade = ReviewGrade.INITIAL.gradeStore,
            minimumOrderPrice = onboardingStoreForm.storeForm.minimumOrderPrice,
            storeStatus = StoreStatus.INIT,
        )
        store.createdBy = roleName
        store.updatedBy = roleName
        return this.storeRepository.save(store)
    }
}