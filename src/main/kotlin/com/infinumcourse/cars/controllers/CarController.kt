package com.infinumcourse.cars.controllers

import com.infinumcourse.cars.entities.Car
import com.infinumcourse.cars.entities.CarInfo
import com.infinumcourse.cars.service.CarService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
class CarController (private val carService: CarService) {

    @GetMapping("/get-all-cars")
    @ResponseBody
    fun getAllcars(): List<Car> {
        return carService.getAllCars()
    }

    @PostMapping("/add-car")
    @ResponseBody
    fun addCar(@RequestBody carAdder: CarAdder): ResponseEntity<Car> {
        val newCar = carService.addCar(carAdder)
        return ResponseEntity(newCar, HttpStatus.OK)
    }

    @GetMapping("/get-car-info/{id}")
    @ResponseBody
    fun getCarInfo(@PathVariable id: UUID): ResponseEntity<CarInfo> {
        val newCarInfo = carService.getCarInfo(id)
        return ResponseEntity(newCarInfo, HttpStatus.OK)
    }

    @GetMapping("/get-all-cars-paged")
    @ResponseBody
    fun getAllCarsPaged(pageable: Pageable): Page<Car>{
        return carService.getAllCarsPaged(pageable)
    }

}