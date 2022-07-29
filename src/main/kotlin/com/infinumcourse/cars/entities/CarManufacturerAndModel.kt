package com.infinumcourse.cars.entities

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "manufacturerandmodels")
data class CarManufacturerAndModel(
    @Id
    val id: UUID = UUID.randomUUID(),

    val manufacturer: String,

    val model: String
)