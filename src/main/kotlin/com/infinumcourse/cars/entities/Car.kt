package com.infinumcourse.cars.entities


import java.time.LocalDate
import java.util.*

data class Car (val manufacturer: String,
                val carmodel: String,
                val vin: String,
                val id: Long, val addingDate: LocalDate, val productionYear: Long){
    val checkUps = LinkedList<CarCheckUp>()
}