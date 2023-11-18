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
@Table(name = "PROJECT", indexes = [
    Index(name = "IDX_PROJECT_APPLICANT", columnList = "APPLICANT_ID"),
    Index(name = "IDX_PROJECT_EXPERT", columnList = "EXPERT_ID")
])
@Entity
open class Project {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @JoinColumn(name = "APPLICANT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var applicantId: Applicant? = null

    @JoinColumn(name = "EXPERT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var expertId: Expert? = null

    @Column(name = "PROJECT_NAME", nullable = false)
    @NotNull
    var projectName: String? = null

    @InstanceName
    @Column(name = "DESCRIPTION", nullable = false, length = 400)
    @NotNull
    var description: String? = null

    @Column(name = "LONGITUDE", nullable = false)
    @NotNull
    var longitude: String? = null

    @Column(name = "LATITUDE", nullable = false)
    @NotNull
    var latitude: String? = null

    @Column(name = "COST", nullable = false)
    @NotNull
    var cost: String? = null

    @Column(name = "PROJECT_FILE_NUMBER", nullable = false)
    @NotNull
    var projectFileNumber: String? = null

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