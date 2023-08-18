package com.example.myapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.myapp.source.remote.UserAPI
import com.example.myapp.source.remote.response.UserListResponse
import com.example.myapp.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {

    var userResponseLiveData = MutableLiveData<NetworkResult<UserListResponse>>()
    suspend fun getUserList(){
        userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.getUserList()
        if (response.isSuccessful){
            userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }else if(response.errorBody()!=null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        }else{
            userResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

}