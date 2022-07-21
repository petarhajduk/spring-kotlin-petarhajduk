package com.infinumcourse

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate
import javax.xml.bind.DatatypeConverter.parseLong

class Test {
    //val repository: CarCheckUpRepository = mockk<CarCheckUpRepository>()
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

@WebMvcTest
class CarCheckUpControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var carCheckUpService: CarCheckUpService

    var long: Long = 0
    private val cars = mutableMapOf<Long, Car>(
        long to Car("Fiat", "Brava", "U9U9BC9UCHTHEO4", 0, LocalDate.now().minusMonths(5), 2002),
        long+1 to Car("Mazda", "6", "N83B89G74BDJC9U", 1, LocalDate.now().minusMonths(2), 2008)
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

}