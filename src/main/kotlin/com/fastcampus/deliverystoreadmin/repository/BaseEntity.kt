package com.fastcampus.deliverystoreadmin.repository

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime

@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
open class BaseEntity {
    @Column(name = "is_deleted")
    val isDeleted: Boolean = false

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: OffsetDateTime = OffsetDateTime.now()

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, updatable = true)
    var updatedAt: OffsetDateTime = OffsetDateTime.now()

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    var createdBy: String? = null

    @LastModifiedBy
    @Column(name = "updated_by")
    var updatedBy: String? = null
}