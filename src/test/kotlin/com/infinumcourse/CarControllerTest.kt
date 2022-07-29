package com.infinumcourse

import com.fasterxml.jackson.databind.ObjectMapper
import com.infinumcourse.cars.controllers.CarAdder
import com.infinumcourse.checkups.controllers.CheckUpAdder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDate
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
){

    @Test
    fun testGetAllCars(){
        //val expectedCars = cars
        mockMvc.get("/get-all-cars")
            .andExpect {
                status { is2xxSuccessful() }
            }
    }

    @Test
    fun addCarTest(){
        mockMvc.post("/add-car") {
            content = objectMapper.writeValueAsString(CarAdder("Peugeot", "2008", "HD9872NDCOD823NF", LocalDate.now() , 2012))
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun addCarCheckUpTest(){
        mockMvc.post("/add-check-up") {
            content = CheckUpAdder(UUID.fromString("4b9cde0f-eb26-4622-8a4a-c96ffac2751d"), LocalDate.now().minusMonths(1), "Marko", 1000)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun getCarInfoTest(){
        mockMvc.get("/get-car-info/4b9cde0f-eb26-4622-8a4a-c96ffac2751d").andExpect { status { is2xxSuccessful() } }
    }

    @Test
    fun getAllCarsPaged(){
        mockMvc.get("/get-all-cars-paged?size=2").andExpect { status { is2xxSuccessful() } }
    }

    @Test
    fun getAllCheckUpsPaged(){
        mockMvc.get("/get-all-checkups-paged?id=bb2f22ca-fa81-4d35-a7cb-a5ebd0ec928c").andExpect { status { is2xxSuccessful() } }
    }
}