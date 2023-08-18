package com.example.myapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.repository.UserRepository
import com.example.myapp.source.remote.response.UserListResponse
import com.example.myapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    var userResponseLiveData : LiveData<NetworkResult<UserListResponse>> = MutableLiveData<NetworkResult<UserListResponse>>()
    fun getUserListRequest() {
        viewModelScope.launch {
            userResponseLiveData = userRepository.getUserList()
        }
    }

}