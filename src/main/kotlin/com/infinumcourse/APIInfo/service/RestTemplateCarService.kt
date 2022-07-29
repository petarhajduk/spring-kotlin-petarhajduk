package com.infinumcourse.APIInfo.service

import com.infinumcourse.APIInfo.entities.CarResponse
import com.infinumcourse.APIInfo.entities.FormatForDataBase
import com.infinumcourse.APIInfo.entities.ManufacturerAndModels
import com.infinumcourse.APIInfo.repository.CarResponseRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Service
@Qualifier("rtCarService")
class RestTemplateCarService(
    private val restTemplate: RestTemplate,
    private val carResponseRepository: CarResponseRepository,
    @Value("\${carcheckup-service.base-url}") private val baseUrl: String
) {


    private val logger = LoggerFactory.getLogger(this::class.java)

    fun getManufacturersAndModels(){
        logger.info("fetching from URL...")
        val JSONObject: Array<ManufacturerAndModels> = restTemplate.getForObject<CarResponse>("$baseUrl").cars

        val manufacturers: List<String> = JSONObject.map { cars -> cars.manufacturer } //combining all manufacturers in one list
        val models = mutableListOf<String>()
        JSONObject.forEach { models.addAll(it.models)} //combining all models in one list

        val dbInput = mutableListOf<FormatForDataBase>()

        for (ma in manufacturers){ //basically creating a list with pairs (manufacturer, model) because that eay I would be able to store them in the database
            for (mo in models){    //manufacturers will repeat, yes
                dbInput.add(FormatForDataBase(ma, mo))
            }
        }

        carResponseRepository.saveAll(dbInput)
    }

}