package com.infinumcourse

import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate


@SpringBootTest
class IntegrationTest @Autowired constructor( private var applicationContext: ApplicationContext,
                                              private val carCheckUpService: CarCheckUpService) {


    @org.junit.jupiter.api.Test
    fun prviIntegracijski(){
        val car = Car("Porsche", "Panamera", "JCN94HRV949BC9", 2, LocalDate.now(), 2018)
        assertThat(carCheckUpService.addCar("Porsche", "Panamera", "JCN94HRV949BC9", 2018)).isNotNull.isEqualTo(car)
    }
}

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootMvcApplicationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var carCheckUpService: CarCheckUpService

    @org.junit.jupiter.api.Test
    fun testGetCarInfoById(){
        val carInfo = carCheckUpService.getCarInfo(0)
        mockMvc.get("/get-car-info/0")
            .andExpect {
                status { is2xxSuccessful() }
                content { carInfo }
            }
    }

    @org.junit.jupiter.api.Test
    fun testGetAllCars(){
        val allCars = carCheckUpService.getAllCars()
        mockMvc.get("/get-all-cars")
            .andExpect {
                status { is2xxSuccessful() }
                content { allCars }
            }
    }
}