package com.ch.travelersteamdatabase.firebaseModel

data class TravelLocation (
    var latitude: Float? = 0.0F,
    var longitude: Float? = 0.0F

){
    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "latitude" to latitude,
            "longitude" to longitude,
        )
    }
}
