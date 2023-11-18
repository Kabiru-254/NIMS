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
@Table(name = "LICENCE_APPLICATION", indexes = [
    Index(name = "IDX_LICENCE_APPLICATION_STEP", columnList = "STEP_ID"),
    Index(name = "IDX_LICENCE_APPLICATION_APPLICATION_CATEGORY", columnList = "APPLICATION_CATEGORY_ID"),
    Index(name = "IDX_LICENCE_APPLICATION_EXPERT", columnList = "EXPERT_ID"),
    Index(name = "IDX_LICENCE_APPLICATION_PROJECT", columnList = "PROJECT_ID"),
    Index(name = "IDX_LICENCE_APPLICATION_APPLICANT", columnList = "APPLICANT_ID")
])
@Entity
open class LicenceApplication {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @JoinColumn(name = "APPLICANT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var applicantId: Applicant? = null

    @JoinColumn(name = "STEP_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var stepId: LicenceApplicationStep? = null

    @Column(name = "STATUS", nullable = false)
    @NotNull
    var status: String? = null

    @Column(name = "STEP_LEVEL", nullable = false)
    @NotNull
    var stepLevel: Int? = null

    @Column(name = "MAX_STEPS", nullable = false)
    @NotNull
    var maxSteps: Int? = null

    @Column(name = "REFERENCE_NUMBER", nullable = false)
    @NotNull
    var referenceNumber: String? = null

    @JoinColumn(name = "APPLICATION_CATEGORY_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var applicationCategoryId: ApplicationCategory? = null

    @JoinColumn(name = "EXPERT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var expertId: Expert? = null

    @JoinColumn(name = "PROJECT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var projectId: Project? = null

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

}