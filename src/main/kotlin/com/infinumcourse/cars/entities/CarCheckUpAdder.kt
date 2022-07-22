package com.infinumcourse.cars.entities

import java.time.LocalDate

class CarCheckUpAdder (
    val carid: Long,
    val checkUpDateTime: LocalDate,
    val worker: String,
    val price: Long
)