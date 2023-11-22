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
@Table(name = "PERMIT_EXPORT_TRANS_BOUNDARY_APPLICATION", indexes = [
    Index(name = "IDX_PERMIT_EXPORT_TRANS_BOUNDARY_APPLICATION_PERMIT_APPLICATION", columnList = "PERMIT_APPLICATION_ID"),
    Index(name = "IDX_PERMIT_EXPORT_TRANS_BOUNDARY_APPLICATION_PERMIT_APPLICANT", columnList = "PERMIT_APPLICANT_ID")
])
@Entity
open class PermitExportTransBoundaryApplication {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @JoinColumn(name = "PERMIT_APPLICATION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    var permitApplicationId: PermitsApplication? = null

    @Column(name = "EXPORTER_NAME", nullable = false)
    @NotNull
    var exporterName: String? = null

    @Column(name = "EXPORTER_ADDRESS", nullable = false)
    @NotNull
    var exporterAddress: String? = null

    @Column(name = "DESTINATION_NAME", nullable = false)
    @NotNull
    var destinationName: String? = null

    @Column(name = "WASTE_TO_BE_TRANSPORTED", nullable = false)
    @NotNull
    var wasteToBeTransported: String? = null

    @JoinColumn(name = "PERMIT_APPLICANT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var permitApplicantId: PermitApplicant? = null

    @Column(name = "DESTINATION_ADDRESS", nullable = false)
    @NotNull
    var destinationAddress: String? = null

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