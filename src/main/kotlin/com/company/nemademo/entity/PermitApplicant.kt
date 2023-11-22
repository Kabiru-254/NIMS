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
@Table(name = "PERMIT_APPLICANT")
@Entity
open class PermitApplicant {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @Column(name = "APPLICANT_TYPE", nullable = false)
    @NotNull
    private var applicantType: Int? = null

    @InstanceName
    @Column(name = "FIRST_NAME")
    var firstName: String? = null

    @Column(name = "LAST_NAME")
    var lastName: String? = null

    @Column(name = "ID_NUMBER_OR_PASSPORT_NUMBER")
    var idNumberOrPassportNumber: String? = null

    @Column(name = "POSTAL_ADDRESS")
    var postalAddress: String? = null

    @Column(name = "RESIDENCE")
    var residence: String? = null

    @Column(name = "ORGANIZATION_NAME")
    var organizationName: String? = null

    @Column(name = "KRA_PIN")
    var kraPin: String? = null

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