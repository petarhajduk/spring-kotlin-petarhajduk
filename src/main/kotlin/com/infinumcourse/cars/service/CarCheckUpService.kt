package com.infinumcourse.cars.service

import com.infinumcourse.cars.entities.*
import com.infinumcourse.cars.repository.CarCheckUpRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class CarCheckUpService(@Qualifier("databaseCarAndCarCheckUpRepository") val repo: CarCheckUpRepository){

    fun addCar(carAdder: CarAdder): Car {
        return repo.insertCar(carAdder)
    }

    fun addCheckUp(carCheckUpAdder: CarCheckUpAdder): CarCheckUp {
        return repo.insertCheckUp(carCheckUpAdder)
    }

    fun getCarInfo(id: Long): CarInfo {
        return repo.getCarInfo(id)
    }

    fun getAllCars(): MutableList<Car> {
        return repo.getAllCars()
    }

    fun getCheckUpsByManufacturer(): Map<String, Long> {
        return repo.getCheckUpsByManufacturer()
    }
}