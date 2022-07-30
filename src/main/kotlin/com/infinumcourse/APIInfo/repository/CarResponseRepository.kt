package com.infinumcourse.APIInfo.repository

import com.infinumcourse.APIInfo.entities.ManufacturerAndModel
import org.springframework.data.repository.Repository
import java.util.*

interface CarResponseRepository: Repository<ManufacturerAndModel, UUID> {
    fun saveAll(manufacturerAndModel: Iterable<ManufacturerAndModel>): Iterable<ManufacturerAndModel>

    fun save(manufacturerAndModel: ManufacturerAndModel) : ManufacturerAndModel

    fun findAll(): Iterable<ManufacturerAndModel>

    fun deleteAll()

    fun count(): Long

    fun findManufacturerAndModelDTOByManufacturer(manufacturer: String): Iterable<ManufacturerAndModel>

    fun findByManufacturerAndModel(manufacturer: String, model: String): ManufacturerAndModel

    fun existsByManufacturerAndModel(manufacturer: String, model: String): Boolean
}