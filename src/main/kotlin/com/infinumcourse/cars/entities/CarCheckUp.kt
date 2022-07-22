package com.infinumcourse.cars.entities

import java.time.LocalDate

class CarCheckUp (val carid: Long,
                  val checkUpDateTime: LocalDate,
                  val id: Long, val worker: String, val price: Long)