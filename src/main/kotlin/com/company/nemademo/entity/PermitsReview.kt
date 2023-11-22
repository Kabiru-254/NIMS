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
@Table(name = "PERMITS_REVIEW", indexes = [
    Index(name = "IDX_PERMITS_REVIEW_PERMIT_APPLICATION", columnList = "PERMIT_APPLICATION_ID"),
    Index(name = "IDX_PERMITS_REVIEW_REVIEWER_USER", columnList = "REVIEWER_USER_ID"),
    Index(name = "IDX_PERMITS_REVIEW_PERMIT_APPLICATION_STATUS_RECOMMENDED", columnList = "PERMIT_APPLICATION_STATUS_RECOMMENDED_ID")
])
@Entity
open class PermitsReview {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @JoinColumn(name = "PERMIT_APPLICATION_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var permitApplicationId: PermitsApplication? = null

    @JoinColumn(name = "REVIEWER_USER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var reviewerUserId: User? = null

    @Column(name = "REVIEWS", nullable = false)
    @NotNull
    var reviews: String? = null

    @JoinColumn(name = "PERMIT_APPLICATION_STATUS_RECOMMENDED_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var permitApplicationStatusRecommended: Recommendation? = null

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