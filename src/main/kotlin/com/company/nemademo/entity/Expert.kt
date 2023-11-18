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
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

@JmixEntity
@Table(name = "EXPERT", indexes = [
    Index(name = "IDX_EXPERT_SECTOR", columnList = "SECTOR_ID"),
    Index(name = "IDX_EXPERT_SUB_SECTOR", columnList = "SUB_SECTOR_ID")
])
@Entity
open class Expert {
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

    @Column(name = "EMAIL_ADDRESS", nullable = false)
    @NotNull
    var emailAddress: String? = null

    @Column(name = "MOBILE_NUMBER", nullable = false)
    @NotNull
    var mobileNumber: String? = null

    @Column(name = "LICENCE_NUMBER", nullable = false)
    @NotNull
    var licenceNumber: String? = null

    @Column(name = "LICENCE_EXPIRY_DATE", nullable = false)
    @NotNull
    var licenceExpiryDate: LocalDate? = null

    @JoinColumn(name = "SECTOR_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var sectorId: Sector? = null

    @JoinColumn(name = "SUB_SECTOR_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var subSectorId: SubSector? = null

    @Column(name = "VERSION", nullable = false)
    @Version
    var version: Int? = null

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    var lastModifiedBy: String? = null

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    var lastModifiedDate: OffsetDateTime? = null

    @CreatedBy
    @Column(name = "CREATED_BY")
    var createdBy: String? = null

    @CreatedDate
    @Column(name = "CREATED_DATE")
    var createdDate: OffsetDateTime? = null

}