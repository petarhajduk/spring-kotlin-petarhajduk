package com.infinumcourse.cars.entities


import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "cars")
data class Car (
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "manufacturerandmodel")
    val manufacturerAndModel: CarManufacturerAndModel,

    val vin: String,

    @Column(name = "addingdate")
    val addingDate: LocalDate,

    @Column(name = "productionyear")
    val productionYear: Long
    )