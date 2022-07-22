package com.infinumcourse.cars.repository

import com.infinumcourse.cars.entities.*

interface CarCheckUpRepository{
    fun insertCar(carAdder: CarAdder): Car
    fun insertCheckUp(carCheckUpAdder: CarCheckUpAdder): CarCheckUp
    fun getCarInfo(id: Long): CarInfo
    fun findById(id: Long): CarCheckUp
    fun deleteById(id: Long): CarCheckUp
    fun findAll(): List<CarCheckUp>
    fun getAllCars(): MutableList<Car>
    fun getCheckUpsByManufacturer(): Map<String, Long>
}

class CarCheckUpNotFoundException(id: Long): RuntimeException("Car check-up ID $id not found")
class CarNotFoundException(id: Long): java.lang.RuntimeException("Car ID $id not found")