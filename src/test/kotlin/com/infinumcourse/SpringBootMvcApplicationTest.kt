package com.infinumcourse

import com.infinumcourse.APIInfo.entities.ManufacturerAndModel
import com.infinumcourse.APIInfo.repository.CarResponseRepository
import com.infinumcourse.cars.entities.Car
import com.infinumcourse.cars.repository.CarRepository
import com.infinumcourse.cars.service.CarService
import com.infinumcourse.checkups.entities.CarCheckUp
import com.infinumcourse.checkups.repository.CheckUpRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootMvcApplicationTest @Autowired constructor(
    private val carRepository: CarRepository,
    private val carResponseRepository: CarResponseRepository,
    private val checkUpRepository: CheckUpRepository
) {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var carService: CarService

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

    @org.junit.jupiter.api.Test
    fun testGetCarInfoById(){//This test will never pass because it is impossible to hit the same ID

        val id = carRepository.findAll().first().id
        val carInfo = carService.getCarInfo(id)
        mockMvc.get("/get-car-info/$id")
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

    @org.junit.jupiter.api.Test
    fun testGetAllCarsPaged(){
        val carsPaged = carService.getAllCarsPaged(Pageable.ofSize(2))
        mockMvc.get("/get-all-cars-paged")
            .andExpect {
                status { is2xxSuccessful() }
                content { carsPaged }
            }
    }
}