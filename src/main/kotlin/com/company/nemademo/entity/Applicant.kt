package com.company.nemademo.entity

import io.jmix.core.entity.annotation.JmixGeneratedValue
import io.jmix.core.metamodel.annotation.InstanceName
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
@Table(name = "APPLICANT", indexes = [
    Index(name = "IDX_APPLICANT_APPLICATION_CATEGORY", columnList = "APPLICATION_CATEGORY_ID")
])
@Entity
open class Applicant {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @InstanceName
    @Column(name = "FIRST_NAME", nullable = false)
    @NotNull
    var firstName: String? = null

    @Column(name = "LAST_NAME", nullable = false)
    @NotNull
    var lastName: String? = null

    @JoinColumn(name = "APPLICATION_CATEGORY_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var applicationCategoryId: ApplicationCategory? = null

    @Column(name = "APPLICANT_TYPE", nullable = false)
    @NotNull
    private var applicantType: Int? = null

    @Column(name = "KRA_PIN", nullable = false)
    @NotNull
    var kraPin: String? = null

    @Column(name = "EMAIL_ADDRESS", nullable = false)
    @NotNull
    var emailAddress: String? = null

    @Column(name = "PHYSICAL_ADDRESS", nullable = false)
    @NotNull
    var physicalAddress: String? = null

    @Column(name = "POSTAL_CODE")
    var postalCode: String? = null

    @Column(name = "POSTAL_TOWN")
    var postalTown: String? = null

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

    fun getApplicantType(): ApplicantType? = applicantType?.let { ApplicantType.fromId(it) }

    fun setApplicantType(applicantType: ApplicantType?) {
        this.applicantType = applicantType?.id
    }
}