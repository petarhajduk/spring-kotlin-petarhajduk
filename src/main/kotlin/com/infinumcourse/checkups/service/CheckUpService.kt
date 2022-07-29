package com.infinumcourse.checkups.service

import com.infinumcourse.cars.repository.CarRepository
import com.infinumcourse.checkups.controllers.CheckUpAdder
import com.infinumcourse.checkups.controllers.CheckUpDTO
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
    fun addCheckUp(checkUpAdder: CheckUpAdder): CheckUpDTO{
        val checkUp = checkUpAdder.toCheckUp { carId ->
            carRepository.findById(carId) ?: throw java.lang.IllegalArgumentException("no car with such id $carId")
        }

        return CheckUpDTO(checkUpRepository.save(checkUp))
    }

    fun getCheckUpsByManufacturer(): Map<String, Long> {
        val map = mutableMapOf<String, Long>()
        val cars = carRepository.findAll()

        cars.forEach{//Iterating through every car in the database to add every manufacturer in the map
            if (!map.containsKey(it.manufacturerAndModel.manufacturer)) map.put(it.manufacturerAndModel.manufacturer, 0)
        }

        var manufacturer: String?
        var temp: Long

        val checkups = checkUpRepository.findAll()
        checkups.forEach{//after each iteration I will increment the value in the map depending on who's car's check up am I currently at
            temp = map[it.car.manufacturerAndModel.manufacturer] ?: throw RuntimeException("The map broke")
            map[it.car.manufacturerAndModel.manufacturer] = temp+1
        }

        return map
    }

    fun getAllCheckUpsPaged(pageable: Pageable, id: UUID): Page<CheckUpDTO> {
        return checkUpRepository.findByCar_Id(id, pageable).map { CheckUpDTO(it) }
    }
}