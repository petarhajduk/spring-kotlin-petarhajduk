package com.infinumcourse.checkups.controllers

import com.infinumcourse.checkups.service.CheckUpService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
class CheckUpController(private val checkUpService: CheckUpService) {

    @GetMapping("/get-check-ups-by-manufacturer")
    @ResponseBody
    fun getCheckUpsByManufacturer(): Map<String, Long> {
        return checkUpService.getCheckUpsByManufacturer()
    }

    @PostMapping("/add-check-up")
    @ResponseBody
    fun addCheckUp(@RequestBody carCheckUpAdder: CheckUpAdder): ResponseEntity<CheckUpDTO>{
        val newCheckUp = checkUpService.addCheckUp(carCheckUpAdder)
        return ResponseEntity(newCheckUp, HttpStatus.OK)
    }

    @GetMapping("/get-all-checkups-paged")
    @ResponseBody
    fun getAllCheckUpsPaged(pageable: Pageable, @RequestParam id: UUID): Page<CheckUpDTO> {
        return checkUpService.getAllCheckUpsPaged(pageable, id)
    }
}