package com.infinumcourse

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class Test {
    val repository: CarCheckUpRepository = mockk<CarCheckUpRepository>()

    @Test
    fun testing(){
        val servis: CarCheckUpService = CarCheckUpService(repository)

        every { repository.insert(any(), any()) } returns 1

        servis.insert(Car("Škoda", "Fabia", "BIJCR9338HBC93948"))

        verify (exactly = 2) { repository.insert(any(), any()) }
    }
}

