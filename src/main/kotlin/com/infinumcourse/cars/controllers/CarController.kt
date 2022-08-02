package com.infinumcourse.cars.controllers

import com.infinumcourse.cars.controllers.dto.*
import com.infinumcourse.cars.entities.Car
import com.infinumcourse.cars.service.CarService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class CarController (
    private val carService: CarService,
    private val carResourceAssembler: CarResourceAssembler,
    private val carInfoResourceAssembler: CarInfoResourceAssembler
    ) {

    @GetMapping("/get-all-cars")
    @ResponseBody
    fun getAllcars(): ResponseEntity<CollectionModel<CarResource>> {
        return ResponseEntity.ok(
            carResourceAssembler.toCollectionModel(carService.getAllCars())
        )
    }

    @PostMapping("/add-car")
    @ResponseBody
    fun addCar(@RequestBody carAdder: CarAdder): ResponseEntity<CarResource> {
        return ResponseEntity.ok(
            carResourceAssembler.toModel(
                carService.addCar(carAdder)
            )
        )
    }

    @GetMapping("/get-car-info/{id}")
    @ResponseBody
    fun getCarInfo(
        @PathVariable id: UUID,
        pageable: Pageable,
    ): ResponseEntity<CarInfoResource> {
        return ResponseEntity.ok(
            carInfoResourceAssembler.toModel(
                carService.getCarInfo(id)
            )
        )
    }

    @GetMapping("/get-all-cars-paged")
    @ResponseBody
    fun getAllCarsPaged(
        pageable: Pageable,
        pagedResourcesAssembler: PagedResourcesAssembler<Car>
    ): ResponseEntity<PagedModel<CarResource>>{
        return ResponseEntity.ok(
            pagedResourcesAssembler.toModel(
                carService.getAllCarsPaged(pageable),
                carResourceAssembler
            )
        )
    }

    @GetMapping("/get-car/{id}")
    @ResponseBody
    fun getCar(@PathVariable id: UUID): ResponseEntity<CarResource>{
        return ResponseEntity.ok(
            carResourceAssembler.toModel(carService.getCar(id))
        )
    }

    @DeleteMapping("/delete-car")
    @ResponseBody
    fun deleteCar(@RequestParam id: UUID){
        carService.deleteCar(id)
    }
}