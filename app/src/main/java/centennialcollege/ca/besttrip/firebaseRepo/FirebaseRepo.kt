package com.ch.travelersteamdatabase.firebaseRepo

import android.content.Context
import android.media.MediaPlayer.OnCompletionListener
import android.util.Log
import com.ch.travelersteamdatabase.MainActivity
import com.ch.travelersteamdatabase.firebaseModel.TravelRoute
import com.ch.travelersteamdatabase.firebaseModel.TravelerUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseRepo (appContext: Context) {
    val db = Firebase.firestore

    companion object{
        const val TAG = "com.ch.travelersteamdatabase.firebaseRepo"
        const val COLLECTION_USERS = "users"
        const val COLLECTION_ROUTE = "routes"
    }

    suspend fun insertTravelerUser(user: TravelerUser): String {
        var result: String = "error"
        withContext(Dispatchers.IO){
            db.collection(COLLECTION_USERS)
                .add(user.modelToHashMapOf())
                .addOnSuccessListener {
                    Log.e(TAG,"User added successfully: ${it.id}")
                    result = it.id
                }
                .addOnFailureListener {
                    Log.e(TAG, "Error on add new user")
                }
                .await()
        }
        return result
    }

    suspend fun getTravelerUserById(id: String): TravelerUser {
        var result: TravelerUser = TravelerUser()
        Log.e(TAG, "Searching user by id: $id")
        withContext(Dispatchers.IO){
            val reference = db.collection(COLLECTION_USERS).document(id)
            reference.get()
                .addOnSuccessListener { document ->
                    if (document.exists()){
                        result = document.toObject<TravelerUser>()!!
                        result._id = document.id
                    }
                }
        }
        return result
    }

    suspend fun travelerUserExist(id: String): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            val reference = db.collection(COLLECTION_USERS).document(id)
            reference.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        result = true
                        Log.e(TAG, "Document found: $document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error on verify document: $id ", exception)
                }
        }
        return result
    }

    suspend fun updateTravelerUser(user: TravelerUser): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            val reference = db.collection(COLLECTION_USERS).document(user._id)
            reference.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        result = true
                        Log.e(TAG, "Document found: $document")
                        reference.set(user.modelToHashMapOf())
                            .addOnSuccessListener {
                                Log.e(TAG, "Successfully updated: ${user._id}")
                            }
                            .addOnFailureListener {
                                Log.e(TAG, "Error on updated: ${user._id} exception: $it")
                            }
                    } else {
                        Log.e(TAG, "User not found")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error on verify document: ${user._id} ", exception)
                }
                .await()
        }
        return result
    }

    suspend fun deleteTravelerUser(user: TravelerUser): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            db.collection(COLLECTION_USERS).document(user._id)
                .delete()
                .addOnSuccessListener {
                    Log.e(TAG, "User deleted successfully: ${user._id}")
                    result = true
                }
                .addOnFailureListener {
                    Log.e(TAG, "Error on delete user: ${user._id}")
                }
                .await()
        }
        return result
    }

    suspend fun insertTravelRoute(route: TravelRoute): String {
        var result = "error"
        withContext(Dispatchers.IO) {
            db.collection(COLLECTION_USERS).document(route.userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        db.collection(COLLECTION_ROUTE)
                            .add(route.modelToHashMapOf())
                            .addOnSuccessListener {
                                result = document.id
                                Log.e(TAG, "Route added successfully: ${it.id}")
                            }
                            .addOnFailureListener {
                                Log.e(TAG, "Error on add new route. Exception: $it")
                            }
                    } else {
                        Log.e(TAG, "Route user (${route.userId}) not found")
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "Error on add new route (validate user). Exception: $it")
                }
                .await()
        }
        return result
    }

    suspend fun getTravelRouteById(id: String): TravelRoute {
        var result: TravelRoute = TravelRoute()
        withContext(Dispatchers.IO) {
            val reference = db.collection(COLLECTION_ROUTE).document(id)
            reference.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        result = document.toObject<TravelRoute>()!!
                        result._id = document.id
                        Log.e(TAG,"ID=${document.id}")
                        Log.e(TAG,"Route founded")
                    }else{
                        Log.e(TAG,"Route not founded")
                    }
                }
                .await()
        }
        Log.e(TAG,"Route founded with id: ${result._id}")
        return result
    }

    suspend fun getTravelRoutesByUserId(userId: String): List<TravelRoute> {
        var result: MutableList<TravelRoute> = mutableListOf()
        withContext(Dispatchers.IO) {
            val reference = db.collection(COLLECTION_ROUTE)
            reference.whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.e(TAG, "Route founded ${document.id}")
                        var actual = document.toObject<TravelRoute>()!!
                        actual._id = document.id
                        result.add(actual)
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "Error on get routes for user: $userId. Exception: $it")
                }
                .await()
        }
        return result
    }

    suspend fun updateTravelRoute(route: TravelRoute): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            val reference = db.collection(COLLECTION_ROUTE).document(route._id)
            reference.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        Log.e(TAG, "Route found: $document")
                        reference.set(route)
                            .addOnSuccessListener {
                                result = true
                                Log.e(TAG, "Successfully updated: ${route._id}")
                            }
                            .addOnFailureListener {
                                Log.e(TAG, "Successfully updated: ${route._id} exception: $it")
                            }
                    } else {
                        Log.e(TAG, "Route not found")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error on verify document: ${route._id} ", exception)
                }
                .await()
        }
        return result
    }

    suspend fun deleteTravelRoute(route: TravelRoute): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            db.collection(COLLECTION_ROUTE).document(route._id)
                .delete()
                .addOnSuccessListener {
                    result = true
                    Log.e(TAG, "Route deleted successfully: ${route._id}")
                }
                .addOnFailureListener {
                    Log.e(TAG, "Error on delete route: ${route._id}. Exception: $it")
                }
                .await()
        }
        return result
    }


}