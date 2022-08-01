package com.infinumcourse

import com.fasterxml.jackson.databind.ObjectMapper
import com.infinumcourse.APIInfo.entities.CarResponse
import com.infinumcourse.APIInfo.entities.ManufacturerAndModels
import com.infinumcourse.APIInfo.service.RestTemplateCarService
import org.assertj.core.api.Assertions
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.MediaType
import org.mockserver.springtest.MockServerTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@MockServerTest
@SpringBootTest
@AutoConfigureMockMvc
class CarsRestControllerTests @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val rtCarService: RestTemplateCarService
) {

    lateinit var mockServerClient: MockServerClient

    @org.junit.jupiter.api.Test
    fun test1(){
        mockServerClient
            .`when`(
                HttpRequest.request()
                    .withPath("https://62d922dd5d893b27b2df0731.mockapi.io/api/v1/cars/1")
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody("""
                        {
                        "cars":
                            [{"manufacturer":"Porsche","models":
                                ["911 Turbo","Cayenne","Panamera"]},
                            {"manufacturer":"Citroen","models":
                                ["C3","C4","C5"]},
                            {"manufacturer":"Volkswagen","models":
                                ["Polo"]},
                            {"manufacturer":"Hyundai","models":
                                ["i30","i20","i35","i10"]}],    
                        "Car":"1"
                        }
                """.trimIndent())
            )

//
//            {"cars":[{"manufacturer":"Porsche","models":["911 Turbo","Cayenne","Panamera"]},
//                {"manufacturer":"Citroen","models":["C3","C4","C5"]},
//                {"manufacturer":"Volkswagen","models":["Polo"]},
//                {"manufacturer":"Hyundai","models":["i30","i20","i35","i10"]}],"Car":"1"}



        val carResponce = rtCarService.getManufacturersAndModels()

        Assertions.assertThat(carResponce).isEqualTo(CarResponse((arrayOf<ManufacturerAndModels>(
            ManufacturerAndModels("Porsche", arrayOf("911 Turbo", "Cayenne", "Panamera")),
            ManufacturerAndModels("Citroen", arrayOf("C3", "C4", "C5")),
            ManufacturerAndModels("Volkswagen", arrayOf("Polo")),
            ManufacturerAndModels("Hyundai", arrayOf("i30","i20","i35","i10"))
        ))))
    }
}