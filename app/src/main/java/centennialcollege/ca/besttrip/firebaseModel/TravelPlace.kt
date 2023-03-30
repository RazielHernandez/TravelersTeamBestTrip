package com.ch.travelersteamdatabase.firebaseModel

data class TravelPlace (
    var name: String,
    var placeId: String,
    var location: TravelLocation,
){
    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "name" to name,
            "placeId" to placeId,
            "location" to location
        )
    }
}