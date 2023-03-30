package com.ch.travelersteamdatabase.firebaseRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.travelersteamdatabase.MainActivity
import com.ch.travelersteamdatabase.firebaseModel.TravelerUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseUserModel: ViewModel() {
    private val _userLiveData = MutableLiveData<TravelerUser>()
    val userLiveData: LiveData<TravelerUser> = _userLiveData

    private val _idLiveData = MutableLiveData<String> ()
    val idLiveData: LiveData<String> = _idLiveData

    private val _completeLiveData = MutableLiveData<Boolean> ()
    val completeLiveData: LiveData<Boolean> = _completeLiveData

    fun insertTravelerUser(user: TravelerUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.insertTravelerUser(user)
            _idLiveData.postValue(data)
        }
    }

    fun getTravelerUserById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.getTravelerUserById(id)
            _userLiveData.postValue(data)
        }
    }

    fun updateTravelerUser(user: TravelerUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val completed = MainActivity.db?.updateTravelerUser(user)
            _completeLiveData.postValue(completed)
        }
    }

    fun deleteTravelerUser(user: TravelerUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val completed = MainActivity.db?.deleteTravelerUser(user)
            _completeLiveData.postValue(completed)
        }
    }

}