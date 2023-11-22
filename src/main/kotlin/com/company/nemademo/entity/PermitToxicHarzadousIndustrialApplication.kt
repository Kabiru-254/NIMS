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
@Table(name = "PERMIT_TOXIC_HARZADOUS_INDUSTRIAL_APPLICATION", indexes = [
    Index(name = "IDX_PERMITTOXICHARZADOUSINDUSTRIALAPPLICATION_PERMITAPPLICATION", columnList = "PERMIT_APPLICATION_ID")
])
@Entity
open class PermitToxicHarzadousIndustrialApplication {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @JoinColumn(name = "PERMIT_APPLICATION_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var permitApplicationId: PermitsApplication? = null

    @Column(name = "IMPORTER_OR_EXPORTER", nullable = false)
    @NotNull
    private var importerOrExporter: Int? = null

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    var name: String? = null

    @Column(name = "ADDRESS")
    var address: String? = null

    @Column(name = "NATURE_OF_BUSINESS", nullable = false)
    @NotNull
    var natureOfBusiness: String? = null

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

    fun getImporterOrExporter(): ImporterOrExporter? = importerOrExporter?.let { ImporterOrExporter.fromId(it) }

    fun setImporterOrExporter(importerOrExporter: ImporterOrExporter?) {
        this.importerOrExporter = importerOrExporter?.id
    }
}