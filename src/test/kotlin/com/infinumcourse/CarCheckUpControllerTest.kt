package com.infinumcourse

import com.fasterxml.jackson.databind.ObjectMapper
import com.infinumcourse.cars.entities.Car
import com.infinumcourse.cars.entities.CarAdder
import com.infinumcourse.cars.entities.CarCheckUpAdder
import com.infinumcourse.cars.service.CarCheckUpService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDate


@WebMvcTest
class CarCheckUpControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
){

    @MockkBean
    lateinit var carCheckUpService: CarCheckUpService

    private val cars = mutableListOf<Car>(
        Car("Fiat", "Brava", "U9U9BC9UCHTHEO4", 0, LocalDate.now().minusMonths(5), 2002),
        Car("Mazda", "6", "N83B89G74BDJC9U", 1, LocalDate.now().minusMonths(2), 2008)
    )


    @BeforeEach
    fun setUp(){
        every { carCheckUpService.getAllCars() } answers { cars }
    }

    @Test
    fun testGetAllCars(){
        val expectedCars = cars
        mockMvc.get("/get-all-cars")
            .andExpect {
                status { is2xxSuccessful() }
                content { expectedCars }
            }
    }

    @Test
    fun addCarTest(){
        mockMvc.post("/add-car") {
            content = objectMapper.writeValueAsString(CarAdder("Peugeot", "2008", "HD9872NDCOD823NF", 2012))
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun addCarCheckUpTest(){
        mockMvc.post("/add-check-up") {
            content = CarCheckUpAdder(1, LocalDate.now().minusMonths(1), "Marko", 1000)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun getCarInfoTest(){
        mockMvc.get("/get-car-info/0").andExpect { status { is2xxSuccessful() } }
    }
}