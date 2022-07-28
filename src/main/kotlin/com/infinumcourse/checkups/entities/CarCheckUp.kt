package com.infinumcourse.checkups.entities

import com.infinumcourse.cars.entities.Car
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "checkups")
class CarCheckUp (

    @ManyToOne
    @JoinColumn(name = "car")
    val car: Car,

    @Column(name = "checkupdate")
    val checkUpDate: LocalDate,

    @Id
    val id: UUID = UUID.randomUUID(),

    val worker: String,

    val price: Long

    )