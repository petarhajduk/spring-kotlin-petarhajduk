package com.infinumcourse.cars.entities


import com.infinumcourse.APIInfo.entities.ManufacturerAndModel
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "cars")
data class Car (
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne//(cascade = [CascadeType.ALL])
    @JoinColumn(name = "manufacturerandmodel")
    val manufacturerAndModel: ManufacturerAndModel,

    val vin: String,

    @Column(name = "addingdate")
    val addingDate: LocalDate,

    @Column(name = "productionyear")
    val productionYear: Long
    )