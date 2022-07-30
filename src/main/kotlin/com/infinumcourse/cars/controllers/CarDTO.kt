package com.infinumcourse.cars.controllers

import com.infinumcourse.cars.entities.Car
import java.time.LocalDate
import java.util.*

data class CarDTO(
    val id: UUID,
    val manufacturerAndModelId: Long,
    val vin: String,
    val addingDate: LocalDate,
    val productionYear: Long
) {

    constructor(car: Car): this(
        car.id,
        car.manufacturerAndModel.id,
        car.vin,
        car.addingDate,
        car.productionYear
    )

}