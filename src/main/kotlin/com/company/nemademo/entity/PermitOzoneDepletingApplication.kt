package com.company.nemademo.entity

import io.jmix.core.entity.annotation.JmixGeneratedValue
import io.jmix.core.metamodel.annotation.JmixEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

@JmixEntity
@Table(name = "PERMIT_OZONE_DEPLETING_APPLICATION", indexes = [
    Index(name = "IDX_PERMIT_OZONE_DEPLETING_APPLICATION_PERMIT_APPLICANT", columnList = "PERMIT_APPLICANT_ID"),
    Index(name = "IDX_PERMIT_OZONE_DEPLETING_APPLICATION_PERMIT_APPLICATION", columnList = "PERMIT_APPLICATION_ID")
])
@Entity
open class PermitOzoneDepletingApplication {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @JoinColumn(name = "PERMIT_APPLICATION_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var permitApplicationId: PermitsApplication? = null

    @Column(name = "REFERENCE_NUMBER", nullable = false)
    @NotNull
    var referenceNumber: String? = null

    @JoinColumn(name = "PERMIT_APPLICANT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    var permitApplicantId: PermitApplicant? = null

    @Column(name = "ODL_LICENCE_NUMBER", nullable = false)
    @NotNull
    var odlLicenceNumber: String? = null

    @Column(name = "CONTROL_PERIOD_START_DATE")
    var controlPeriodStartDate: LocalDate? = null

    @Column(name = "CONTROL_PERIOD_END_DATE")
    var controlPeriodEndDate: LocalDate? = null

    @Column(name = "CONTACT_PERSON_NAME", nullable = false)
    @NotNull
    var contactPersonName: String? = null

    @Column(name = "PIN", nullable = false)
    @NotNull
    var pin: String? = null

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