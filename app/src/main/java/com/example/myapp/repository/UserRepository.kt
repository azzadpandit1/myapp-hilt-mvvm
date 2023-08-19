package com.example.myapp.repository

import android.accounts.NetworkErrorException
import com.example.myapp.source.remote.ApiEndPoint
import com.example.myapp.utils.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiEndPoint: ApiEndPoint) {
/*
    suspend fun getUserList() = flow {
//        val userResponseLiveData = MutableLiveData<NetworkResult<UserListResponse>>()
//        userResponseLiveData.postValue(NetworkResult.Loading())
        *//*emit(NetworkResult.Loading())
        val response = apiEndPoint.getUserList()
        Log.d("TAG", "getUserList: request ")
        if (response.isSuccessful){
            Log.e("TAG", "getUserList: "+response.body()?.data.toString() )
           emit(NetworkResult.Success(response.body()))
//            userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }else if(response.errorBody()!=null){
            Log.e("TAG", "getUserList: error ", )
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//            userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
            emit(NetworkResult.Error(errorObj.getString("message")))
        }else{
            Log.e("TAG", "getUserList: null ", )
//            userResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
            emit(NetworkResult.Error("Something Went Wrong"))
        }
//        return userResponseLiveData*//*
    }*/

    suspend fun getUserList() = flow {
        emit(NetworkResult.Loading())
        val response = apiEndPoint.getUserList()
        emit(NetworkResult.Success(response))
    }.catch { e ->
        when (e) {
            is HttpException -> {
                if (e.code() == 500) {
                    emit(NetworkResult.Error("something went wrong ?"))
                } else if (e.code() == 404) {
                    emit(NetworkResult.Error("Url not found"))
                } else if (e.code() == 401) {
                    /*val respose = Gson().fromJson(
                        e.response()?.errorBody()?.charStream(),
                        UserData::class.java
                    )*/
                    emit(NetworkResult.Error(e.message))
                } else {
                    emit(NetworkResult.Error(e.message))
                }
            }
            is NetworkErrorException -> {
                emit(NetworkResult.Error(e.message))
            }
        }
    }

}