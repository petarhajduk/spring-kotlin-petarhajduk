package com.infinumcourse.cars.controllers.dto

import com.infinumcourse.cars.controllers.CarController
import com.infinumcourse.cars.entities.CarInfo
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component
import java.util.*

@Component
class CarInfoResourceAssembler : RepresentationModelAssemblerSupport<CarInfo, CarInfoResource>(
    CarController::class.java, CarInfoResource::class.java
) {

    private val noPagination = Pageable.unpaged()
    private val nullAssembler = PagedResourcesAssembler<CarInfo>(null, null)


    override fun toModel(entity: CarInfo): CarInfoResource {
        return createModelWithId(entity.car.id, entity).apply {
            add(
                linkTo<CarController> {
                    getCarInfo(carid, noPagination)
                }.withRel("cars")
            )
        }
    }

    override fun instantiateModel(entity: CarInfo): CarInfoResource{
        return CarInfoResource(
            carid = entity.car.id,
            checkUpNeeded = entity.checkUpNeeded
        )
    }

}

@Relation(collectionRelation = IanaLinkRelations.ITEM_VALUE)
data class CarInfoResource(
    val carid: UUID,
    val checkUpNeeded: Boolean
): RepresentationModel<CarInfoResource>()