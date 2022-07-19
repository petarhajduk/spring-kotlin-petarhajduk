package com.infinumcourse

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class Test {
    val repository: CarCheckUpRepository = mockk<CarCheckUpRepository>()
    val testrepo: InMemoryCarAndCarCheckUpRepository = mockk<InMemoryCarAndCarCheckUpRepository>()

//    @Test
//    fun testing(){
//        val servis: CarCheckUpService = CarCheckUpService(repository)
//
//        every { repository.insert(any(), any()) } returns 1
//
//        servis.insert(Car("Škoda", "Fabia", "BIJCR9338HBC93948"))
//
//        verify (exactly = 2) { repository.insert(any(), any()) }
//    }

    @Test
    fun testing2(){
        val servis: CarCheckUpService = CarCheckUpService(testrepo)

        every { testrepo.insertCar(any(), any(), any(), any()) }

        servis.addCar("Fiat", "Punto", "BKEN48C99RH4XHNUR", 2003)

        verify (exactly = 1) { testrepo.insertCar(any(), any(), any(), any()) }
    }
}

