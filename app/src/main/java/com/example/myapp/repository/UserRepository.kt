package com.example.myapp.repository

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.room.withTransaction
import com.example.myapp.source.local.room.Database.RestaurantDatabase
import com.example.myapp.source.networkBoundResource
import com.example.myapp.source.remote.ApiEndPoint
import com.example.myapp.utils.NetworkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiEndPoint: ApiEndPoint ,  private val db: RestaurantDatabase) {
    /*suspend fun getUserList() = flow {
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
                    emit(NetworkResult.Error(e.message))
                } else {
                    emit(NetworkResult.Error(e.message))
                }
            }
            is NetworkErrorException -> {
                emit(NetworkResult.Error(e.message))
            }
        }
    }*/


    private val restaurantDao = db.restaurantDao()
    fun getRestaurants()= networkBoundResource(
        query = {
            Log.d("TAG", "getRestaurants: Query", )
            restaurantDao.getAllRestaurants()
        },
        fetch = {
            Log.e("TAG", "getRestaurants: Fetch ", )
            delay(2000)
            apiEndPoint.getUserList()
        },
        saveFetchResult = {  restaurants ->
            db.withTransaction {
                restaurantDao.deleteAllRestaurants()
                restaurantDao.insertRestaurants(restaurants.data)
                Log.e("TAG", "getRestaurants: saveFetchResult", )
            }
        }
    )









}