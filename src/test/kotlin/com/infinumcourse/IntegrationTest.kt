package com.infinumcourse

import com.infinumcourse.cars.entities.Car
import com.infinumcourse.cars.entities.CarAdder
import com.infinumcourse.cars.service.CarCheckUpService
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import java.time.LocalDate


@SpringBootTest
class IntegrationTest @Autowired constructor( private var applicationContext: ApplicationContext,
                                              private val carCheckUpService: CarCheckUpService
) {


    @org.junit.jupiter.api.Test
    fun prviIntegracijski(){
        val car = Car("Porsche", "Panamera", "JCN94HRV949BC9", 2, LocalDate.now(), 2020)
        assertThat(carCheckUpService.addCar(CarAdder("Porsche", "Panamera", "JCN94HRV949BC9", 2020))).isNotNull.isEqualTo(car)
    }
}