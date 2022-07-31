package com.infinumcourse.cars.controllers.dto

import com.infinumcourse.APIInfo.entities.ManufacturerAndModel
import com.infinumcourse.cars.controllers.CarController
import com.infinumcourse.cars.entities.Car
import com.infinumcourse.checkups.controllers.CheckUpController
import com.infinumcourse.checkups.entities.CarCheckUp
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class CarResourceAssembler :  RepresentationModelAssemblerSupport<Car, CarResource>(
    CarController::class.java, CarResource::class.java
) {

    private val noPagination = Pageable.unpaged()
    private val nullAssembler = PagedResourcesAssembler<CarCheckUp>(null, null)


    override fun toModel(entity: Car): CarResource {
        return createModelWithId(entity.id, entity).apply {
            add(
                linkTo<CheckUpController> {
                    getAllCheckUpsPaged(entity.id, noPagination, nullAssembler)
                }.withRel("checkups")
            )
        }
    }

    override fun instantiateModel(entity: Car): CarResource {
        return CarResource(entity.id, entity.manufacturerAndModel, entity.vin, entity.addingDate, entity.productionYear)
    }

}


@Relation(collectionRelation = IanaLinkRelations.ITEM_VALUE)
data class CarResource(
    val id: UUID,
    val manufacturerAndModel: ManufacturerAndModel,
    val vin: String,
    val addingDate: LocalDate,
    val productionYear: Long
): RepresentationModel<CarResource>()