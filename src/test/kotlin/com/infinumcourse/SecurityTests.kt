package com.infinumcourse

import com.fasterxml.jackson.databind.ObjectMapper
import com.infinumcourse.APIInfo.entities.ManufacturerAndModel
import com.infinumcourse.APIInfo.repository.CarResponseRepository
import com.infinumcourse.cars.controllers.dto.CarAdder
import com.infinumcourse.cars.entities.Car
import com.infinumcourse.cars.repository.CarRepository
import com.infinumcourse.checkups.entities.CarCheckUp
import com.infinumcourse.checkups.repository.CheckUpRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTests @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val checkUpRepository: CheckUpRepository,
    private val carRepository: CarRepository,
    private val carResponseRepository: CarResponseRepository

) {

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
        val porsche2 = Car(manufacturerAndModel = porschePanamera, vin = "ND726SS0D740HOK", addingDate = LocalDate.parse("2022-02-12"), productionYear = 2019)

        carRepository.saveAll(listOf(fiat, mazda, porsche, porsche2))


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
    @WithMockUser(authorities = ["SCOPE_admin", "SCOPE_user"])
    fun addCarAdminUserAccessTest() {
        mockMvc.post ("/add-car"){
            content = objectMapper.writeValueAsString(
                CarAdder(ManufacturerAndModel(manufacturer = "Porsche", model = "Panamera"),
                    "HD9872NDCOD823NF", LocalDate.now() , 2012))
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_admin"])
    fun getAllCarsAdminAccessTest() {
        mockMvc.get ("/get-all-cars")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_user"])
    fun getAllCarsUserAccessTest() {
        mockMvc.get ("/get-all-cars")
            .andExpect {
                status { isForbidden() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_admin", "SCOPE_user"])
    fun getCarAccessTest(){
        val car = carRepository.findByVin("U9U9BC9UCHTHEO4")
        mockMvc.get ("/get-car/${car.id}")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_user"])
    fun addCheckUpUserAccessTest() {
        mockMvc.post ("/add-check-up")
            .andExpect {
                status { isForbidden() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_admin", "SCOPE_user"])
    fun getAllCheckUpsPagedAccess(){
        val car = carRepository.findByVin("U9U9BC9UCHTHEO4")
        mockMvc.get ("/get-all-checkups-paged?id=${car.id}&order=ASC")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }


    @Test
    @WithMockUser(authorities = ["SCOPE_admin"])
    fun deleteCarAdminAccessTest(){
        val car = carRepository.findByVin("ND726SS0D740HOK")
        mockMvc.delete ("/delete-car?id=${car.id}")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_user"])
    fun deleteCarUserAccessTest(){
        val car = carRepository.findByVin("U9U9BC9UCHTHEO4")
        mockMvc.delete ("/delete-car?id=${car.id}")
            .andExpect {
                status { isForbidden() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_admin"])
    fun deleteCheckUpAdminAccessTest(){
        val checkup = checkUpRepository.findAll().first()
        mockMvc.delete ("/delete-check-up?id=${checkup.id}")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_user"])
    fun deleteCheckUpUserAccessTest(){
        val checkup = checkUpRepository.findAll().first()
        mockMvc.delete ("/delete-check-up?id=${checkup.id}")
            .andExpect {
                status { isForbidden() }
            }
            .andReturn().response.contentAsString
    }



}