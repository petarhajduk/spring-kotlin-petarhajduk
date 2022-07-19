package com.infinumcourse

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [SpringBootMvcApplication::class])
class IntegrationTest @Autowired constructor( private var applicationContext: ApplicationContext,
                                              private val carCheckUpService: CarCheckUpService) {


    @org.junit.jupiter.api.Test
    fun prviIntegracijski(){
        assertThat(carCheckUpService.addCar("Porsche", "Panamera", "JCN94HRV949BC9", 2018)).isNotNull.isEqualTo(1)
    }



}