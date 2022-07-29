package com.infinumcourse

import com.infinumcourse.cars.entities.Car
import com.infinumcourse.cars.entities.CarManufacturerAndModel
import com.infinumcourse.cars.repository.CarRepository
import com.infinumcourse.checkups.entities.CarCheckUp
import com.infinumcourse.checkups.repository.CheckUpRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import java.time.LocalDate

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JPATests @Autowired constructor(
    val carRepository: CarRepository,
    val checkUpRepository: CheckUpRepository
){

    @BeforeEach
    fun setup(){

        checkUpRepository.deleteAll()
        carRepository.deleteAll()

        val fiat = Car(manufacturerAndModel = CarManufacturerAndModel(manufacturer = "Fiat", model = "Brava"), vin =  "U9U9BC9UCHTHEO4", addingDate = LocalDate.parse("2020-07-07"), productionYear = 2001)
        val mazda = Car(manufacturerAndModel = CarManufacturerAndModel(manufacturer = "Mazda", model = "6"), vin = "N83B89G74BDJC9U", addingDate = LocalDate.parse("2018-04-04"), productionYear = 2008)
        val porsche = Car(manufacturerAndModel = CarManufacturerAndModel(manufacturer = "Porsche", model = "Panamera"), vin = "HDOW0C37F7E73BM", addingDate = LocalDate.parse("2021-10-10"), productionYear = 2018)

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
    fun canFindAllCheckUps(){
        val allCheckUps = checkUpRepository.findAll()
        Assertions.assertThat(allCheckUps.size).isEqualTo(6)
    }

    @Test
    fun canFindAllCarsPaged(){
        val pageable = PageRequest.of(0, 2)
        val allCars = carRepository.findAll(pageable)
        Assertions.assertThat(allCars.totalPages).isEqualTo(2)
        Assertions.assertThat(allCars.content[0].manufacturerAndModel.model).isEqualTo("Brava")
    }

    @Test
    fun findByProductionYearIfExists(){
        val carsFrom2001 = carRepository.findByProductionYear(2001)
        Assertions.assertThat(carsFrom2001.size).isEqualTo(1)

        val carsFrom2002 = carRepository.findByProductionYear(2002)
        Assertions.assertThat(carsFrom2002.size).isEqualTo(0)
    }

//    @Test
//    fun findCheckUpsByManufacturer(){
//        val checkUpsOfManufacturer = checkUpRepository.findByCar_Manufacturer("Mazda")
//        Assertions.assertThat(checkUpsOfManufacturer.size).isEqualTo(2)
//    }

    @Test
    fun findCheckUpsByWorkerAndPrice(){
        val checkUpsByWorkerAndPrice = checkUpRepository.findByWorkerAndPrice("Goran", 800)
        Assertions.assertThat(checkUpsByWorkerAndPrice.size).isEqualTo(1)
    }
}