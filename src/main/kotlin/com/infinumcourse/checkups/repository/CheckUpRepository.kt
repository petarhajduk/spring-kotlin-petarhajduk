package com.infinumcourse.checkups.repository

import com.infinumcourse.checkups.entities.CarCheckUp
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import java.util.*

interface CheckUpRepository: Repository<CarCheckUp, UUID> {

    fun save(checkUp: CarCheckUp): CarCheckUp

    fun saveAll(checkUp: Iterable<CarCheckUp>): List<CarCheckUp>

    fun findAll(): List<CarCheckUp>

    fun findAll(pageable: Pageable): Page<CarCheckUp>

    fun findById(id: UUID): CarCheckUp?

    fun deleteAll()

    fun findByCar_Id(id: UUID, pageable: Pageable): Page<CarCheckUp>

    fun findByCar_Id(id: UUID): List<CarCheckUp>

    fun findByWorkerAndPrice(worker: String, price: Long): List<CarCheckUp>

    @Query(value = "select manufacturer, count(checkups.id) from checkups join cars on checkups.car = cars.id join manufacturerandmodels on cars.manufacturerandmodel = manufacturerandmodels.id group by manufacturer", nativeQuery = true)
    fun findCountCheckUpsByManufacturer(): List<String>
}