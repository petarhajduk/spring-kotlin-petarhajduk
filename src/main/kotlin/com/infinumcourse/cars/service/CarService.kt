package com.infinumcourse.cars.service

import com.infinumcourse.APIInfo.entities.ManufacturerAndModel
import com.infinumcourse.APIInfo.repository.CarResponseRepository
import com.infinumcourse.APIInfo.service.RestTemplateCarService
import com.infinumcourse.cars.controllers.dto.CarAdder
import com.infinumcourse.cars.entities.Car
import com.infinumcourse.cars.entities.CarInfo
import com.infinumcourse.cars.repository.CarRepository
import com.infinumcourse.checkups.repository.CheckUpRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period
import java.util.*
@Service
class CarService(
    val carRepository: CarRepository,
    val checkUpRepository: CheckUpRepository,
    val carResponseRepository: CarResponseRepository,
    val rtCarService: RestTemplateCarService
    ){

    //@Cacheable("car_added")
    fun addCar(carAdder: CarAdder): Car {
        val data = carResponseRepository.findAll()
        val manufacturers: List<String> = data.map { data -> data.manufacturer }
        val models: List<String> = data.map { data -> data.model }

        if (!manufacturers.contains(carAdder.manufacturerAndModel.manufacturer)) throw IllegalArgumentException("Manufacturer (${carAdder.manufacturerAndModel.manufacturer}) does not exist")
        if (!models.contains(carAdder.manufacturerAndModel.model)) throw IllegalArgumentException("Car model (${carAdder.manufacturerAndModel.model}) does not exist")

        val car = Car(manufacturerAndModel = carResponseRepository.findByManufacturerAndModel(carAdder.manufacturerAndModel.manufacturer, carAdder.manufacturerAndModel.model), vin = carAdder.vin, addingDate = carAdder.addingDate, productionYear = carAdder.productionYear)

        return carRepository.save(car)
    }


    fun getCarInfo(id: UUID): CarInfo {
        val car = carRepository.findById(id) ?: throw IllegalArgumentException("no car with such id")
        val onlyCheckUpsOfGivenCarId = checkUpRepository.findByCar_Id(id)

        var checkUpNeeded = true
        onlyCheckUpsOfGivenCarId.forEach{
            if (Period.between(it.checkUpDate, LocalDate.now()).years == 0) checkUpNeeded = false
        }

        return CarInfo(car, checkUpNeeded)
    }

    fun getAllCars(): List<Car> {
        return carRepository.findAll()
    }

    fun getAllCarsPaged(pageable: Pageable): Page<Car> {
        return carRepository.findAll(pageable)
    }

    fun getAllManufacturersAndModels(): Iterable<ManufacturerAndModel> {
        return carResponseRepository.findAll()
    }

    fun getCar(id: UUID): Car {
        return carRepository.findById(id)
    }
}

