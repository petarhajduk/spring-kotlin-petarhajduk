package com.infinumcourse

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [ApplicationConfiguration::class])
class IntegrationTest @Autowired constructor( private var applicationContext: ApplicationContext,
                                              private val carCheckUpService: CarCheckUpService) {


    @org.junit.jupiter.api.Test
    fun prviIntegracijski(){
        assertThat(carCheckUpService.insert(Car("nissan", "model", "mankwec2b2h93b0"))).isNotNull.isEqualTo(1)
    }


    
}