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
@Table(name = "CLEARANCE_CERTIFICATE_REVIEW", indexes = [
    Index(name = "IDX_CLEARANCE_CERTIFICATE_REVIEW_CLEARANCE_CERT_APPLICATION", columnList = "CLEARANCE_CERT_APPLICATION_ID"),
    Index(name = "IDX_CLEARANCE_CERTIFICATE_REVIEW_REVIWER", columnList = "REVIEWER_ID"),
    Index(name = "IDX_CLEARANCE_CERTIFICATE_REVIEW_RECOMMENDATION_STATUS", columnList = "RECOMMENDATION_STATUS_ID")
])
@Entity
open class ClearanceCertificateReview {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @JoinColumn(name = "CLEARANCE_CERT_APPLICATION_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var clearanceCertApplicationId: ClearanceCertificateApplication? = null

    @JoinColumn(name = "REVIEWER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    var reviewerId: User? = null

    @Column(name = "COMMENTS")
    var comments: String? = null

    @JoinColumn(name = "RECOMMENDATION_STATUS_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var recommendationStatusId: Recommendation? = null

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