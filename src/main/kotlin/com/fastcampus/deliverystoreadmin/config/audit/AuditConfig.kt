package com.fastcampus.deliverystoreadmin.config.audit

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing


@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
class AuditConfig {

    @Bean
    fun auditorAware(): AuditorAwareImpl {
        return AuditorAwareImpl()
    }
}