package com.infinumcourse.checkups.controllers.dto

import com.infinumcourse.cars.controllers.CarController
import com.infinumcourse.checkups.controllers.CheckUpController
import com.infinumcourse.checkups.entities.CarCheckUp
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class CheckUpResourceAssembler:
    RepresentationModelAssemblerSupport<CarCheckUp, CheckUpResource>(
        CheckUpController::class.java, CheckUpResource::class.java
    )
{
    override fun toModel(entity: CarCheckUp): CheckUpResource {
        return createModelWithId(entity.id, entity).apply {
            add(
                linkTo<CarController> {
                    getCar(entity.car.id)
                }.withRel("cars")
            )
        }
    }

    override fun instantiateModel(entity: CarCheckUp): CheckUpResource {
        return CheckUpResource(
            carid = entity.car.id,
            checkUpDate = entity.checkUpDate,
            id = entity.id,
            worker = entity.worker,
            price = entity.price
        )
    }
}


@Relation(collectionRelation = IanaLinkRelations.ITEM_VALUE)
data class CheckUpResource(
    val carid: UUID,
    val checkUpDate: LocalDate,
    val id: UUID,
    val worker: String,
    val price: Long
) : RepresentationModel<CheckUpResource>()