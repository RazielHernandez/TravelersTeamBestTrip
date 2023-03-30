package com.ch.travelersteamdatabase.firebaseRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.travelersteamdatabase.MainActivity
import com.ch.travelersteamdatabase.firebaseModel.TravelRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseRouteModel: ViewModel() {
    private val _routeLiveData = MutableLiveData<List<TravelRoute>>()
    val routeLiveData: LiveData<List<TravelRoute>> = _routeLiveData

    private val _idLiveData = MutableLiveData<String> ()
    val idLiveData: LiveData<String> = _idLiveData

    private val _completeLiveData = MutableLiveData<Boolean> ()
    val completeLiveData: LiveData<Boolean> = _completeLiveData

    fun insertTravelRoute(route: TravelRoute) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.insertTravelRoute(route)
            _idLiveData.postValue(data)
        }
    }

    fun getTravelRouteById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.getTravelRouteById(id)
            _routeLiveData.postValue(listOf(data) as List<TravelRoute>?)
        }
    }

    fun getTravelRouteByUser(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.getTravelRoutesByUserId(id)
            _routeLiveData.postValue(data)
        }
    }

    fun updateTravelRoute(route: TravelRoute) {
        viewModelScope.launch(Dispatchers.IO) {
            val completed = MainActivity.db?.updateTravelRoute(route)
            _completeLiveData.postValue(completed)
        }
    }

    fun deleteTravelRoute(route: TravelRoute) {
        viewModelScope.launch(Dispatchers.IO) {
            val completed = MainActivity.db?.deleteTravelRoute(route)
            _completeLiveData.postValue(completed)
        }
    }
}