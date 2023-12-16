package com.fastcampus.deliverystoreadmin.config.audit

import jakarta.annotation.Nonnull
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.time.temporal.TemporalAccessor
import java.util.*

@Component("dateTimeProvider")
class CustomDateTimeProvider : DateTimeProvider {
    @Nonnull
    override fun getNow(): Optional<TemporalAccessor> {
        return Optional.of(OffsetDateTime.now())
    }
}