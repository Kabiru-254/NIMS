package com.company.nemademo.entity

import io.jmix.core.metamodel.datatype.EnumClass

enum class RequiredOrNot(private val id: Int) : EnumClass<Int> {
    REQUIRED(10),
    NOT_REQUIRED(20);

    override fun getId() = id

    companion object {

        @JvmStatic
        fun fromId(id: Int): RequiredOrNot? = RequiredOrNot.values().find { it.id == id }
    }
}