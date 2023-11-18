package com.company.nemademo.entity

import io.jmix.core.entity.annotation.JmixGeneratedValue
import io.jmix.core.metamodel.annotation.JmixEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.util.*

@JmixEntity
@Table(name = "APPLICATION_STEP", indexes = [
    Index(name = "IDX_APPLICATION_STEP_LICENCE", columnList = "LICENCE_ID"),
    Index(name = "IDX_APPLICATION_STEP_APPLICATION_CATEGORY", columnList = "APPLICATION_CATEGORY_ID")
])
@Entity
open class LicenceApplicationStep {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @JoinColumn(name = "LICENCE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var licenceId: LicenceType? = null

    @JoinColumn(name = "APPLICATION_CATEGORY_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var applicationCategoryId: ApplicationCategory? = null

    @Column(name = "STEP_NAME", nullable = false, length = 400)
    @NotNull
    var stepName: String? = null

    @Column(name = "STEP_COUNT", nullable = false)
    @NotNull
    var stepCount: Int? = null

    @Column(name = "REQUIRES_UPLOAD", nullable = false)
    @NotNull
    private var requiresUpload: Int? = null

    @Column(name = "MAX_STEPS", nullable = false)
    @NotNull
    var maxSteps: Int? = null

    @Column(name = "VERSION", nullable = false)
    @Version
    var version: Int? = null

    @CreatedBy
    @Column(name = "CREATED_BY")
    var createdBy: String? = null

    @CreatedDate
    @Column(name = "CREATED_DATE")
    var createdDate: OffsetDateTime? = null

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    var lastModifiedBy: String? = null

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    var lastModifiedDate: OffsetDateTime? = null

    fun getRequiresUpload(): RequiredOrNot? = requiresUpload?.let { RequiredOrNot.fromId(it) }

    fun setRequiresUpload(requiresUpload: RequiredOrNot?) {
        this.requiresUpload = requiresUpload?.id
    }
}