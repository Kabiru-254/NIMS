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
@Table(name = "CASE_MANAGEMENT", indexes = [
    Index(name = "IDX_CASE_MANAGEMENT_CASE_OWNER", columnList = "CASE_OWNER_ID")
])
@Entity
open class CaseManagement {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @Column(name = "COURT_NAME", nullable = false)
    @NotNull
    var courtName: String? = null

    @Column(name = "DATE_INITIATED")
    var dateInitiated: LocalDate? = null

    @Column(name = "REFERENCE_NUMBER")
    var referenceNumber: String? = null

    @Column(name = "CASE_TYPE")
    private var caseType: Int? = null

    @JoinColumn(name = "CASE_OWNER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var caseOwnerId: CaseOwnerAdvocates? = null

    @Column(name = "CASE_RESOLUTION_DETAILS")
    var caseResolutionDetails: String? = null

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

    fun getCaseType(): CaseType? = caseType?.let { CaseType.fromId(it) }

    fun setCaseType(caseType: CaseType?) {
        this.caseType = caseType?.id
    }
}