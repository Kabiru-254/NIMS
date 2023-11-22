package com.company.nemademo.entity

import io.jmix.core.metamodel.datatype.EnumClass

enum class CaseType(private val id: Int) : EnumClass<Int> {
    CRIMINAL(10),
    CIVIL(20);

    override fun getId() = id

    companion object {

        @JvmStatic
        fun fromId(id: Int): CaseType? = CaseType.values().find { it.id == id }
    }
}