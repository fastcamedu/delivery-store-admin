package com.fastcampus.deliverystoreadmin.config.audit

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class AuditorAwareImpl: AuditorAware<String> {

    @Value("\${server.role-name}")
    private lateinit var roleName: String

    override fun getCurrentAuditor(): Optional<String> {
        SecurityContextHolder.getContext().authentication?.let {
            return Optional.of(it.name)
        }
        return Optional.of(roleName)
    }
}