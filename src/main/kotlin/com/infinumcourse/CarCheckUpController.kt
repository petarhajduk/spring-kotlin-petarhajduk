package com.infinumcourse

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@Controller
class CarCheckUpController (private val carCheckUpService: CarCheckUpService) {

    @GetMapping("/get-all-cars")
    @ResponseBody
    fun getAllcars(): Map<Long, Car>{
        return carCheckUpService.getAllCars()
    }

    @PostMapping("/add-car")
    @ResponseBody
    fun addCar(@RequestBody manufacturer: String, carmodel: String, vin: String, productionYear: Long): ResponseEntity<Car> {
        val newCar = carCheckUpService.addCar(manufacturer, carmodel, vin, productionYear)
        return ResponseEntity(newCar, HttpStatus.OK)
    }

    @PostMapping("/add-check-up")
    @ResponseBody
    fun addCheckUp(@RequestBody carid: Long, checkUpDateTime: LocalDateTime, worker: String, price: Long): ResponseEntity<CarCheckUp>{
        val newCheckUp = carCheckUpService.addCheckUp(carid, checkUpDateTime, worker, price)
        return ResponseEntity(newCheckUp, HttpStatus.OK)
    }

    @GetMapping("/get-car-info/{id}")
    @ResponseBody
    fun getCarInfo(@PathVariable id: Long): ResponseEntity<CarInfo> {
        val newCarInfo = carCheckUpService.getCarInfo(id)
        return ResponseEntity(newCarInfo, HttpStatus.OK)
    }

    @GetMapping("/get-check-ups-by-manufacturer")
    @ResponseBody
    fun getCheckUpsByManufacturer(): Map<String, Long> {
        return carCheckUpService.getCheckUpsByManufacturer()
    }
}