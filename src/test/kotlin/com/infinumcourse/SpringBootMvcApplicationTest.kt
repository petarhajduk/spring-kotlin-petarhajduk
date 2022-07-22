package com.infinumcourse

import com.infinumcourse.cars.service.CarCheckUpService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

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