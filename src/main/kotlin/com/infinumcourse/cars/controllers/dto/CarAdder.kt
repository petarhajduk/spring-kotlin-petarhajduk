package com.infinumcourse.cars.controllers.dto

import com.infinumcourse.APIInfo.entities.ManufacturerAndModel
import com.infinumcourse.cars.entities.Car
import java.time.LocalDate

class CarAdder(
    val manufacturerAndModel: ManufacturerAndModel,
    val vin: String,
    val addingDate: LocalDate,
    val productionYear: Long
    ){
    fun toCar(manufacturerAndModel: ManufacturerAndModel) = Car(
        manufacturerAndModel = manufacturerAndModel,
        vin = vin,
        addingDate = addingDate,
        productionYear = productionYear)
}