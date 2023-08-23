package com.example.myapp.source.remote.repository

import android.accounts.NetworkErrorException
import com.example.myapp.source.local.room.Database.UserDatabase
import com.example.myapp.source.local.room.model.GetUserResponse
import com.example.myapp.source.local.room.model.UserModel
import com.example.myapp.source.remote.ApiEndPoint
import com.example.myapp.utils.NetworkResult
import com.example.myapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiEndPoint: ApiEndPoint ,  private val db: UserDatabase) {

    // work with online and offline cache

    suspend fun getOfflineCache() = flow {
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