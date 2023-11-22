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
@Table(name = "INCIDENT_MANAGEMENT", indexes = [
    Index(name = "IDX_INCIDENT_MANAGEMENT_INCIDENT_STATUS", columnList = "INCIDENT_STATUS_ID"),
    Index(name = "IDX_INCIDENT_MANAGEMENT_INCIDENT_CATEGORY", columnList = "INCIDENT_CATEGORY_ID")
])
@Entity
open class IncidentManagement {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @Column(name = "INCIDENT_NUMBER", nullable = false)
    @NotNull
    var incidentNumber: String? = null

    @Column(name = "INCIDENT_SUMMARY", nullable = false)
    @NotNull
    var incidentSummary: String? = null

    @Column(name = "INCIDENT_CLASSIFICATION", nullable = false)
    @NotNull
    private var incidentClassification: Int? = null

    @Column(name = "INCIDENT_DETAILS", nullable = false)
    @NotNull
    var incidentDetails: String? = null

    @Column(name = "CLIENT_NAME", nullable = false)
    @NotNull
    var clientName: String? = null

    @Column(name = "CLIENT_PHONE_NUMBER", nullable = false)
    @NotNull
    var clientPhoneNumber: String? = null

    @Column(name = "CLIENT_EMAIL_ADDRESS", nullable = false)
    @NotNull
    var clientEmailAddress: String? = null

    @Column(name = "INCIDENT_DATE", nullable = false)
    @NotNull
    var incidentDate: LocalDate? = null

    @Column(name = "ASSIGNED_OFFICER_ID")
    var assignedOfficerId: Int? = null

    @Column(name = "OFFICER_NOTES", nullable = false)
    @NotNull
    var officerNotes: String? = null

    @JoinColumn(name = "INCIDENT_STATUS_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var incidentStatusId: IncidentStatuses? = null

    @JoinColumn(name = "INCIDENT_CATEGORY_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var incidentCategoryId: IncidentCategories? = null

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

    fun getIncidentClassification(): IncidenceClassifications? = incidentClassification?.let { IncidenceClassifications.fromId(it) }

    fun setIncidentClassification(incidentClassification: IncidenceClassifications?) {
        this.incidentClassification = incidentClassification?.id
    }
}