package com.infinumcourse

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class CarCheckUpController (private val carCheckUpService: CarCheckUpService) {

    @GetMapping("/get-all-cars")
    @ResponseBody
    fun getAllcars(): Map<Long, Car>{
        return carCheckUpService.getAllCars()
    }

    @PostMapping("/add-car")
    @ResponseBody
    fun addCar(@RequestBody carAdder: CarAdder): ResponseEntity<Car> {
        val newCar = carCheckUpService.addCar(carAdder.manufacturer, carAdder.carmodel, carAdder.vin, carAdder.productionYear)
        return ResponseEntity(newCar, HttpStatus.OK)
    }

    @PostMapping("/add-check-up")
    @ResponseBody
    fun addCheckUp(@RequestBody carCheckUpAdder: CarCheckUpAdder): ResponseEntity<CarCheckUp>{
        val newCheckUp = carCheckUpService.addCheckUp(carCheckUpAdder.carid, carCheckUpAdder.checkUpDateTime, carCheckUpAdder.worker, carCheckUpAdder.price)
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