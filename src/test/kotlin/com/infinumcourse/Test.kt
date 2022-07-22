package com.infinumcourse

import com.infinumcourse.cars.entities.CarAdder
import com.infinumcourse.cars.repository.DatabaseCarAndCarCheckUpRepository
import com.infinumcourse.cars.service.CarCheckUpService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class Test {
    val testrepo: DatabaseCarAndCarCheckUpRepository = mockk<DatabaseCarAndCarCheckUpRepository>()

    @Test
    fun testing2(){
        val servis: CarCheckUpService = CarCheckUpService(testrepo)

        every { testrepo.insertCar(any()) }

        servis.addCar(CarAdder("Fiat", "Punto", "BKEN48C99RH4XHNUR", 2003))

        verify (exactly = 1) { testrepo.insertCar(any()) }
    }
}