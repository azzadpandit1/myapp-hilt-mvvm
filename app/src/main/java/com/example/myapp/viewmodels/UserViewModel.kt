package com.example.myapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp.repository.UserRepository
import com.example.myapp.source.remote.response.UserListResponse
import com.example.myapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {

//    var getUserData = MutableLiveData<NetworkResult<UserListResponse>>()

/*    fun getUserListRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUserList().collectLatest {
                getUserData.postValue(it)
            }
        }
    }*/
//val restaurants = userRepository.getRestaurants().asLive()

        val getUserData = repository.getRestaurants().asLiveData()

}