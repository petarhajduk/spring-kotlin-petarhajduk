package com.infinumcourse

import com.infinumcourse.cars.controllers.CarAdder
import com.infinumcourse.cars.entities.Car
import com.infinumcourse.cars.service.CarService
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import java.time.LocalDate
import java.util.*


@SpringBootTest
class IntegrationTest @Autowired constructor( private var applicationContext: ApplicationContext,
                                              private val carCheckUpService: CarService
) {


    @org.junit.jupiter.api.Test

    //This test will never be correct because ids will never be the same
    fun prviIntegracijski(){
        val car = Car(UUID.randomUUID(), "Porsche", "Panamera", "JCN94HRV949BC9", LocalDate.now(), 2020)
        assertThat(carCheckUpService.addCar(CarAdder("Porsche", "Panamera", "JCN94HRV949BC9", LocalDate.now(), 2020))).isNotNull.isEqualTo(car)
    }
}