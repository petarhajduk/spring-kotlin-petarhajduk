package com.infinumcourse.APIInfo.service

import com.infinumcourse.APIInfo.entities.CarResponse
import com.infinumcourse.APIInfo.entities.ManufacturerAndModel
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

    //@CacheEvict("apiFetch")
    fun getManufacturersAndModels(){
        logger.info("fetching from URL...")

        val JSONObject: Array<ManufacturerAndModels> =
            restTemplate.getForObject<CarResponse>("$baseUrl").cars

        val models = mutableListOf<String>()
        JSONObject.forEach { models.addAll(it.models)} //combining all models in one list


        if (!carResponseRepository.findAll().any()){
            val dbInput = mutableListOf<ManufacturerAndModel>()

            for (manu in JSONObject){ //basically creating a list with pairs (manufacturer, model) because that way I would be able to store them in the database
                for (model in manu.models){
                    dbInput.add(ManufacturerAndModel(manufacturer = manu.manufacturer, model = model))
                }
            }

            carResponseRepository.saveAll(dbInput)
            return
        } else {
            val numOfRows = carResponseRepository.count()
            if (numOfRows == models.size.toLong()) return

            for (o in JSONObject){
                for (model in o.models){
                    if (!carResponseRepository.existsByManufacturerAndModel(o.manufacturer, model)){
                        carResponseRepository.save(ManufacturerAndModel(manufacturer = o.manufacturer, model = model))
                    }
                }
            }
        }
    }
}