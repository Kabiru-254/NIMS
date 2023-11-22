package com.company.nemademo.entity

import io.jmix.core.metamodel.datatype.EnumClass

enum class ImporterOrExporter(private val id: Int) : EnumClass<Int> {
    IMPORTER(10),
    EXPORTER(20);

    override fun getId() = id

    companion object {

        @JvmStatic
        fun fromId(id: Int): ImporterOrExporter? = ImporterOrExporter.values().find { it.id == id }
    }
}