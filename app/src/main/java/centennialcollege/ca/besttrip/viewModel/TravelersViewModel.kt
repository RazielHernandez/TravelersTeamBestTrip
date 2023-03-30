package centennialcollege.ca.besttrip.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import centennialcollege.ca.besttrip.datamodel.TravelerUserList
import centennialcollege.ca.besttrip.repo.network.TravelersService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TravelersViewModel: ViewModel() {

    private val _users = MutableLiveData<TravelerUserList>()
    val users: LiveData<TravelerUserList> = _users

    fun getProductList() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = TravelersService.retrofit.getAllUsers()
            _users.postValue(data)
        }
    }
}