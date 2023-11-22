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
@Table(name = "PERMIT_TOXIC_MINING_EXTRACTION_APPLICATION", indexes = [
    Index(name = "IDX_PERMITTOXICMININGEXTRACTIONAPPLICATION_PERMITAPPLICATION", columnList = "PERMIT_APPLICATION_ID"),
    Index(name = "IDX_PERMIT_TOXIC_MINING_EXTRACTION_APPLICATION_PERMIT_APPLICANT", columnList = "PERMIT_APPLICANT_ID")
])
@Entity
open class PermitToxicMiningExtractionApplication {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @JoinColumn(name = "PERMIT_APPLICATION_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var permitApplicationId: PermitsApplication? = null

    @JoinColumn(name = "PERMIT_APPLICANT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var permitApplicantId: PermitApplicant? = null

    @Column(name = "MANUFACTURER_NAME", nullable = false)
    @NotNull
    var manufacturerName: String? = null

    @Column(name = "MANUFACTURER_ADDRESS", nullable = false)
    @NotNull
    var manufacturerAddress: String? = null

    @CreatedBy
    @Column(name = "CREATED_BY")
    var createdBy: String? = null

    @CreatedDate
    @Column(name = "CREATED_DATE")
    var createdDate: OffsetDateTime? = null

    @Column(name = "VERSION", nullable = false)
    @Version
    var version: Int? = null

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    var lastModifiedBy: String? = null

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    var lastModifiedDate: OffsetDateTime? = null

}