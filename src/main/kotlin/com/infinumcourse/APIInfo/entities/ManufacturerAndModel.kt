package com.infinumcourse.APIInfo.entities

import javax.persistence.*


@Entity
@Table(name = "manufacturerandmodels")
data class ManufacturerAndModel(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manufacturersandmodels_seq")
    @SequenceGenerator(name = "manufacturersandmodels_seq", sequenceName = "manufacturersandmodels_seq", allocationSize = 1)
    val id: Long = 0,

    val manufacturer: String,

    val model: String
    )

