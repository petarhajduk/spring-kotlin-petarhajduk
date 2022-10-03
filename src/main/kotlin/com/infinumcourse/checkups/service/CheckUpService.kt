package com.infinumcourse.checkups.service

import com.infinumcourse.cars.repository.CarRepository
import com.infinumcourse.checkups.controllers.dto.CheckUpAdder
import com.infinumcourse.checkups.entities.CarCheckUp
import com.infinumcourse.checkups.repository.CheckUpRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CheckUpService(
    val carRepository: CarRepository,
    val checkUpRepository: CheckUpRepository
) {
    fun addCheckUp(checkUpAdder: CheckUpAdder): CarCheckUp {
        val checkUp = checkUpAdder.toCheckUp { carId ->
            carRepository.findById(carId) ?: throw java.lang.IllegalArgumentException("no car with such id $carId")
        }

        return checkUpRepository.save(checkUp)
    }

    //@Query(value = "select manufacturer, count(checkups.id) from checkups natural join cars natural join manufacturerandmodels")
    fun getCheckUpsByManufacturer(): List<String> {
        return checkUpRepository.findCountCheckUpsByManufacturer()
    }

    fun getAllCheckUpsPaged(id: UUID, pageable: Pageable, order: String?): Page<CarCheckUp> {
        if (order == "ASC") return checkUpRepository.findByCar_IdOrderByCheckUpDate(id, pageable)
        else if (order == "DESC") return checkUpRepository.findByCar_IdOrderByCheckUpDateDesc(id, pageable)
        else throw IllegalArgumentException("Order should be either ASC or DESC")
    }

    fun deleteCheckUp(id: UUID) {
        checkUpRepository.deleteById(id)
    }
}