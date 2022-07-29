package com.infinumcourse.checkups.repository

import com.infinumcourse.checkups.entities.CarCheckUp
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository
import java.util.*

@Qualifier("checkup")
interface CheckUpRepository: Repository<CarCheckUp, UUID> {

    fun save(checkUp: CarCheckUp): CarCheckUp

    fun saveAll(checkUp: Iterable<CarCheckUp>): Iterable<CarCheckUp>

    fun findAll(): List<CarCheckUp>

    fun findAll(pageable: Pageable): Page<CarCheckUp>

    fun findById(id: UUID): CarCheckUp?

    fun deleteAll()

    fun findByCar_Id(id: UUID, pageable: Pageable): Page<CarCheckUp>

//    fun findByCar_Manufacturer(manufacturer: String): List<CarCheckUp>

    fun findByWorkerAndPrice(manufacturer: String, price: Long): List<CarCheckUp>
}