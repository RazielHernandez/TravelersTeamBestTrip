package com.ch.travelersteamdatabase.firebaseModel

data class TravelerVehicle(
    var year: Int? = 1900,
    var maker: String = "Unknown",
    var model: String = "Unknown"
){
    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "year" to year,
            "maker" to maker,
            "model" to model,
        )
    }
}
