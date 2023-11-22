package com.company.nemademo.entity

import io.jmix.core.metamodel.datatype.EnumClass

enum class IncidenceClassifications(private val id: Int) : EnumClass<Int> {
    A(10),
    B(20),
    C(30);

    override fun getId() = id

    companion object {

        @JvmStatic
        fun fromId(id: Int): IncidenceClassifications? = IncidenceClassifications.values().find { it.id == id }
    }
}