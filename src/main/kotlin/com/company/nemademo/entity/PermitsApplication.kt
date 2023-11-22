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
@Table(name = "PERMITS_APPLICATION", indexes = [
    Index(name = "IDX_PERMITS_APPLICATION_PERMIT_APPLICANT", columnList = "PERMIT_APPLICANT_ID"),
    Index(name = "IDX_PERMITS_APPLICATION_PERMIT_TYPE", columnList = "PERMIT_TYPE_ID"),
    Index(name = "IDX_PERMITS_APPLICATION_SECTION_HEAD", columnList = "SECTION_HEAD_ID"),
    Index(name = "IDX_PERMITS_APPLICATION_REVIEWER_USER", columnList = "REVIEWER_USER_ID")
])
@Entity
open class PermitsApplication {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @Column(name = "REFERENCE_NUMBER", nullable = false)
    @NotNull
    var referenceNumber: String? = null

    @Column(name = "FILE_REFERENCE_NUMBER", nullable = false)
    @NotNull
    var fileReferenceNumber: String? = null

    @Column(name = "APPLICATION_STATUS")
    var applicationStatus: String? = null

    @Column(name = "REVIEW_STATUS")
    var reviewStatus: String? = null

    @JoinColumn(name = "SECTION_HEAD_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    var sectionHeadId: User? = null

    @JoinColumn(name = "REVIEWER_USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    var reviewerUserId: User? = null

    @JoinColumn(name = "PERMIT_APPLICANT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var permitApplicantId: PermitApplicant? = null

    @JoinColumn(name = "PERMIT_TYPE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var permitTypeId: PermitsTypeAndRequirements? = null

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