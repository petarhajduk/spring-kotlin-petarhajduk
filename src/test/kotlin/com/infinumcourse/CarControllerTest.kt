package com.infinumcourse

import com.fasterxml.jackson.databind.ObjectMapper
import com.infinumcourse.APIInfo.entities.ManufacturerAndModel
import com.infinumcourse.APIInfo.repository.CarResponseRepository
import com.infinumcourse.cars.controllers.dto.CarAdder
import com.infinumcourse.cars.entities.Car
import com.infinumcourse.cars.repository.CarRepository
import com.infinumcourse.checkups.controllers.dto.CheckUpAdder
import com.infinumcourse.checkups.entities.CarCheckUp
import com.infinumcourse.checkups.repository.CheckUpRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDate


@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val checkUpRepository: CheckUpRepository,
    private val carRepository: CarRepository,
    private val carResponseRepository: CarResponseRepository
){

    @BeforeEach
    fun setup(){

        checkUpRepository.deleteAll()
        carRepository.deleteAll()
        carResponseRepository.deleteAll()

        val fiatBrava = ManufacturerAndModel(manufacturer = "Fiat", model = "Brava")
        val mazda6 = ManufacturerAndModel(manufacturer = "Mazda", model = "6")
        val porschePanamera = ManufacturerAndModel(manufacturer = "Porsche", model = "Panamera")

        carResponseRepository.saveAll(listOf(fiatBrava, mazda6, porschePanamera))

        val fiat = Car(manufacturerAndModel = fiatBrava, vin =  "U9U9BC9UCHTHEO4", addingDate = LocalDate.parse("2020-07-07"), productionYear = 2001)
        val mazda = Car(manufacturerAndModel = mazda6, vin = "N83B89G74BDJC9U", addingDate = LocalDate.parse("2018-04-04"), productionYear = 2008)
        val porsche = Car(manufacturerAndModel = porschePanamera, vin = "HDOW0C37F7E73BM", addingDate = LocalDate.parse("2021-10-10"), productionYear = 2018)

        carRepository.saveAll(listOf(fiat, mazda, porsche))


        val checkups = listOf<CarCheckUp>(
            CarCheckUp(car = fiat, checkUpDate = LocalDate.parse("2020-07-12"), worker = "Marko", price = 800),
            CarCheckUp(car = fiat, checkUpDate = LocalDate.parse("2021-07-07"), worker = "Marko", price = 800),
            CarCheckUp(car = fiat, checkUpDate = LocalDate.parse("2022-12-07"), worker = "Marko", price = 1000),
            CarCheckUp(car = mazda, checkUpDate = LocalDate.parse("2020-04-09"), worker = "Goran", price = 1000),
            CarCheckUp(car = mazda, checkUpDate = LocalDate.parse("2021-05-10"), worker = "Goran", price = 800),
            CarCheckUp(car = porsche, checkUpDate = LocalDate.parse("2022-06-06"), worker = "Zoki", price = 2000)
        )

        checkUpRepository.saveAll(checkups)

    }

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
            content = objectMapper.writeValueAsString(CarAdder(ManufacturerAndModel(manufacturer = "Porsche", model = "Panamera"), "HD9872NDCOD823NF", LocalDate.now() , 2012))
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun addCarCheckUpTest(){
        mockMvc.post("/add-check-up") {
            val id = carRepository.findByProductionYear(2008).first().id
            content = CheckUpAdder(id, LocalDate.now().minusMonths(1), "Marko", 1000)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun getCarInfoTest(){
        val id = carRepository.findAll().first().id.toString()
        mockMvc.get("/get-car-info/$id").andExpect { status { is2xxSuccessful() } }
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