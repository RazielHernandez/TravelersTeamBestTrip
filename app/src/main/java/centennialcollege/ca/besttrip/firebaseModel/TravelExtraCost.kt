package com.ch.travelersteamdatabase.firebaseModel

data class TravelExtraCost(
    var tag: String,
    var cost: Float,
){
    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "tag" to tag,
            "cost" to cost,
        )
    }
}
