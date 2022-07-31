package com.infinumcourse.APIInfo.controllers.dto

import com.infinumcourse.APIInfo.controllers.ManufacturerAndModelController
import com.infinumcourse.APIInfo.entities.ManufacturerAndModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component


@Component
class ManufacturerAndModelResourceAssembler: RepresentationModelAssemblerSupport<ManufacturerAndModel, ManufacturerAndModelResource>(
    ManufacturerAndModel::class.java, ManufacturerAndModelResource::class.java
) {

    override fun toModel(entity: ManufacturerAndModel): ManufacturerAndModelResource {
        return createModelWithId(entity.id, entity).apply {
            add(
                linkTo<ManufacturerAndModelController> {
                    getAllManufacturersAndModels()
                }.withRel("manufacturerandmodels")
            )
        }
    }

    override fun instantiateModel(entity: ManufacturerAndModel): ManufacturerAndModelResource {
        return ManufacturerAndModelResource(
            manufacturer = entity.manufacturer,
            model = entity.model
        )
    }

}


@Relation(collectionRelation = IanaLinkRelations.ITEM_VALUE)
data class ManufacturerAndModelResource(
    val manufacturer: String,
    val model: String
): RepresentationModel<ManufacturerAndModelResource>()