package com.infinumcourse.APIInfo.entities

class ManufacturerAndModelDTO(
    val id: Long,
    val manufacturer:String,
    val model:String
) {
    constructor(manufacturerAndModel: ManufacturerAndModel): this(
        manufacturerAndModel.id,
        manufacturerAndModel.manufacturer,
        manufacturerAndModel.model
    )
}