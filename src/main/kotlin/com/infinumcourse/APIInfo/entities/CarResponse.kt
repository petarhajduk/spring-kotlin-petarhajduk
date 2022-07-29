package com.infinumcourse.APIInfo.entities

import com.fasterxml.jackson.annotation.JsonProperty

//{
//    "cars":
//        [{"manufacturer":"Porsche","models":["911 Turbo","Cayenne","Panamera"]},
//            {"manufacturer":"Citroen","models":["C3","C4","C5"]},
//            {"manufacturer":"Volkswagen","models":["Polo"]},
//            {"manufacturer":"Hyundai","models":["i30","i20","i35","i10"]}],
//    "Car":"1"
//}

class CarResponse(
    @JsonProperty("cars") val cars: Array<ManufacturerAndModels>
)

class ManufacturerAndModels(
    val manufacturer: String,
    val models: Array<String>
)
