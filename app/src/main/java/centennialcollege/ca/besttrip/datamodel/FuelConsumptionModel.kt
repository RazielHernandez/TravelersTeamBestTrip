package centennialcollege.ca.besttrip.datamodel

data class FuelConsumptionModel(
    var year: String = "",
    var maker: String = "",
    var model: String = "",
    var fuelOnCity: Float = 0F,
    var fuelOnHighway: Float = 0F,
    var fuelOnCombined: Float = 0F,
)
