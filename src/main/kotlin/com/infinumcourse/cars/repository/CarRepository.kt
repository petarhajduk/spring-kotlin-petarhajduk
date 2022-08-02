package com.infinumcourse.cars.repository

import com.infinumcourse.cars.entities.Car
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository
import java.util.*

interface CarRepository : Repository<Car, UUID>{

    fun save(car: Car): Car

    fun saveAll(car: Iterable<Car>): List<Car>

    fun findAll(): List<Car>

    fun findAll(pageable: Pageable): Page<Car>

    fun findById(id: UUID): Car

    fun deleteAll()

    fun findByVin(vin: String): Car

    fun findByProductionYear(year: Long): List<Car>

    fun deleteById(id: UUID)
}