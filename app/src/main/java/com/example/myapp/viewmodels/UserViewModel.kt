package com.example.myapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.myapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {

        val getUserData = repository.getRestaurants().asLiveData()
        fun getRequestApi() {
                Log.e("TAG", "RequestApi: ", )
                repository.getRestaurants()
        }

}