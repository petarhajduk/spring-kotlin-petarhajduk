package com.infinumcourse

import com.infinumcourse.cars.controllers.CarAdder
import com.infinumcourse.cars.repository.CarRepository
import com.infinumcourse.cars.service.CarService
import com.infinumcourse.checkups.repository.CheckUpRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.LocalDate

class Test {
    val carRepository: CarRepository = mockk<CarRepository>()
    val checkUpRepository: CheckUpRepository = mockk<CheckUpRepository>()

    @Test
    fun testing2(){
        val servis: CarService = CarService(carRepository, checkUpRepository)

        every { carRepository.save(any()) }

        servis.addCar(CarAdder("Fiat", "Punto", "BKEN48C99RH4XHNUR", LocalDate.now(), 2003))

        verify (exactly = 1) { carRepository.save(any()) }
    }
}