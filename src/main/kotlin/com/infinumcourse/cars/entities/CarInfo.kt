package com.infinumcourse.cars.entities

import com.infinumcourse.checkups.controllers.CheckUpDTO

class CarInfo(
    val car: Car,
    val checkUps: MutableSet<CheckUpDTO>,
    val checkUpNeeded: Boolean
    )