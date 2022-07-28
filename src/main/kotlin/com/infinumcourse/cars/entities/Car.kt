package com.infinumcourse.cars.entities


import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "cars")
data class Car (
    @Id
    val id: UUID = UUID.randomUUID(),

    val manufacturer: String,

    val carmodel: String,

    val vin: String,

    @Column(name = "addingdate")
    val addingDate: LocalDate,

    @Column(name = "productionyear")
    val productionYear: Long
    )