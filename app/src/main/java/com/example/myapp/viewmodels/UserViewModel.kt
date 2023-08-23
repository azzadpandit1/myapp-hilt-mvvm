package com.example.myapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp.source.local.room.model.GetUserResponse
import com.example.myapp.source.remote.repository.UserRepository
import com.example.myapp.source.local.room.model.UserModel
import com.example.myapp.utils.NetworkResult
import com.example.myapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {

    var getUserData = MutableLiveData<NetworkResult<GetUserResponse>>()
    fun getCacheFromNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getOfflineCache().collectLatest {
                getUserData.postValue(it)
            }
        }
    }


}