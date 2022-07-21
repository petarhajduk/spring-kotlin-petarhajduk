package com.infinumcourse

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CarCheckUpService(@Qualifier("inMemoryCarAndCarCheckUpRepository") val repo: CarCheckUpRepository){

    fun addCar(manufacturer: String, carmodel: String, vin: String, productionYear: Long): Car {
        return repo.insertCar(manufacturer, carmodel, vin, productionYear)
    }

    fun addCheckUp(carid: Long, checkUpDateTime: LocalDateTime, worker: String, price: Long): CarCheckUp {
        return repo.insertCheckUp(carid, checkUpDateTime, worker, price)
    }

    fun getCarInfo(id: Long): CarInfo {
        return repo.getCarInfo(id)
    }

    fun getAllCars(): Map<Long, Car> {
        return repo.getAllCars()
    }

    fun getCheckUpsByManufacturer(): Map<String, Long> {
        return repo.getCheckUpsByManufacturer()
    }
}