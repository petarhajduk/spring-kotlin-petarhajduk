package com.infinumcourse

import com.infinumcourse.cars.service.CarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootMvcApplicationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var carService: CarService

    @org.junit.jupiter.api.Test
    fun testGetCarInfoById(){
        val carInfo = carService.getCarInfo(UUID.fromString("4b9cde0f-eb26-4622-8a4a-c96ffac2751d"))
        mockMvc.get("/get-car-info/4b9cde0f-eb26-4622-8a4a-c96ffac2751d")
            .andExpect {
                status { is2xxSuccessful() }
                content { carInfo }
            }
    }

    @org.junit.jupiter.api.Test
    fun testGetAllCars(){
        val allCars = carService.getAllCars()
        mockMvc.get("/get-all-cars")
            .andExpect {
                status { is2xxSuccessful() }
                content { allCars }
            }
    }
}