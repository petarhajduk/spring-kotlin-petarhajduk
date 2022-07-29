package com.infinumcourse.cars.controllers

import com.infinumcourse.cars.entities.Car
import java.time.LocalDate

class CarAdder(
    val manufacturer: String,
    val carmodel: String,
    val vin: String,
    val addingDate: LocalDate,
    val productionYear: Long
    ){
    fun toCar() = Car(
        manufacturer = manufacturer,
        carmodel = carmodel,
        vin = vin,
        addingDate = addingDate,
        productionYear = productionYear)
}