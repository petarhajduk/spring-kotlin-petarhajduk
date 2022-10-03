package com.infinumcourse.checkups.controllers.dto

import com.infinumcourse.cars.entities.Car
import com.infinumcourse.checkups.entities.CarCheckUp
import java.time.LocalDate
import java.util.*

class CheckUpAdder (
    val carid: UUID,
    val checkUpDate: LocalDate,
    val worker: String,
    val price: Long
){
    fun toCheckUp(carFetcher: (UUID) -> Car) = CarCheckUp(
        car = carFetcher.invoke(carid),
        checkUpDate = checkUpDate,
        worker = worker,
        price = price
    )
}