package com.infinumcourse.checkups.controllers

import com.infinumcourse.checkups.controllers.dto.CheckUpAdder
import com.infinumcourse.checkups.controllers.dto.CheckUpResource
import com.infinumcourse.checkups.controllers.dto.CheckUpResourceAssembler
import com.infinumcourse.checkups.entities.CarCheckUp
import com.infinumcourse.checkups.service.CheckUpService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class CheckUpController(
    private val checkUpService: CheckUpService,
    private val checkUpResourcesAssembler: CheckUpResourceAssembler
    ) {

    @GetMapping("/get-check-ups-by-manufacturer")
    @ResponseBody
    fun getCheckUpsByManufacturer(): List<String> {
        return checkUpService.getCheckUpsByManufacturer()
    }

    @PostMapping("/add-check-up")
    @ResponseBody
    fun addCheckUp(@RequestBody carCheckUpAdder: CheckUpAdder): ResponseEntity<CheckUpResource>{
        return ResponseEntity.ok(
            checkUpResourcesAssembler.toModel(
                checkUpService.addCheckUp(carCheckUpAdder)
            )
        )
    }

    @GetMapping("/get-all-checkups-paged")
    @ResponseBody
    fun getAllCheckUpsPaged(
        @RequestParam id: UUID,
        @RequestParam order: String?,
        pageable: Pageable,
        pagedResourcesAssembler: PagedResourcesAssembler<CarCheckUp>
    ): ResponseEntity<PagedModel<CheckUpResource>> {
        return ResponseEntity.ok(
            pagedResourcesAssembler.toModel(
                checkUpService.getAllCheckUpsPaged(id, pageable, order),
                checkUpResourcesAssembler
            )
        )
    }

    @DeleteMapping("/delete-check-up")
    @ResponseBody
    fun deleteCar(@RequestParam id: UUID){
        checkUpService.deleteCheckUp(id)
    }
}