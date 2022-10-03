package com.infinumcourse.APIInfo.controllers

import com.infinumcourse.APIInfo.controllers.dto.ManufacturerAndModelResource
import com.infinumcourse.APIInfo.controllers.dto.ManufacturerAndModelResourceAssembler
import com.infinumcourse.cars.service.CarService
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ManufacturerAndModelController (
    private val carService: CarService,
    private val manufacturerAndModelResourceAssembler: ManufacturerAndModelResourceAssembler
) {

    @GetMapping("/get-all-manufacturers-and-models")
    @ResponseBody
    fun getAllManufacturersAndModels(): ResponseEntity<CollectionModel<ManufacturerAndModelResource>> {
        return ResponseEntity.ok(
            manufacturerAndModelResourceAssembler.toCollectionModel(
                carService.getAllManufacturersAndModels()
            )
        )
    }

}