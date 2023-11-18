package com.company.nemademo.entity

import io.jmix.core.metamodel.datatype.EnumClass

enum class ApplicantType(private val id: Int) : EnumClass<Int> {
    COMPANY(10),
    INDIVIDUAL(20);

    override fun getId() = id

    companion object {

        @JvmStatic
        fun fromId(id: Int): ApplicantType? = ApplicantType.values().find { it.id == id }
    }
}