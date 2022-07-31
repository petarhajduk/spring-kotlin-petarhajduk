package com.infinumcourse.checkups.controllers.dto

import com.infinumcourse.checkups.entities.CarCheckUp
import java.time.LocalDate
import java.util.*

data class CheckUpDTO(
    val carid: UUID,
    val checkUpDate: LocalDate,
    val id: UUID,
    val worker: String,
    val price: Long
    ) {
    constructor(carCheckUp: CarCheckUp): this(
        carCheckUp.car.id,
        carCheckUp.checkUpDate,
        carCheckUp.id,
        carCheckUp.worker,
        carCheckUp.price
    )
}