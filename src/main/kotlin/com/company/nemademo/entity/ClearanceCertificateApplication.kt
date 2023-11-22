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
@Table(name = "CLEARANCE_CERTIFICATE_APPLICATION", indexes = [
    Index(name = "IDX_CLEARANCE_CERTIFICATE_APPLICATION_CERTIFICATE_TYPE", columnList = "CERTIFICATE_TYPE_ID"),
    Index(name = "IDX_CLEARANCE_CERTIFICATE_APPLICATION_SECTION_HEAD", columnList = "SECTION_HEAD_ID"),
    Index(name = "IDX_CLEARANCE_CERTIFICATE_APPLICATION_REVIEWER", columnList = "REVIEWER_ID")
])
@Entity
open class ClearanceCertificateApplication {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @JoinColumn(name = "CERTIFICATE_TYPE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var certificateTypeId: ClearanceCertificatesRequirements? = null

    @Column(name = "REFERENCE_NUMBER", nullable = false)
    @NotNull
    var referenceNumber: String? = null

    @Column(name = "FILE_REFERENCE_NUMBER", nullable = false)
    @NotNull
    var fileReferenceNumber: String? = null

    @JoinColumn(name = "SECTION_HEAD_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    var sectionHeadId: User? = null

    @JoinColumn(name = "REVIEWER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    var reviewerId: User? = null

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