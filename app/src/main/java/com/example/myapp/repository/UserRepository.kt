package com.example.myapp.repository

import android.util.Log
import androidx.room.withTransaction
import com.example.myapp.source.local.room.Database.UserDatabase
import com.example.myapp.source.networkBoundResource
import com.example.myapp.source.remote.ApiEndPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiEndPoint: ApiEndPoint ,  private val db: UserDatabase) {

    private val restaurantDao = db.userDao()
    fun getRestaurants()= networkBoundResource(
        // pass in the logic to query data from the database
        query = {
            Log.d("TAG", "getRestaurants: Query", )
            restaurantDao.getAllRestaurants()
        },
        // pass in the logic to fetch data from the api
        fetch = {
            Log.e("TAG", "getRestaurants: Fetch ", )
            delay(2000)
            apiEndPoint.getUserList()
        },
        //pass in the logic to save the result to the local cache
        saveFetchResult = {  restaurants ->
            db.withTransaction {
                restaurantDao.deleteAllRestaurants()
                restaurantDao.insertRestaurants(restaurants.data)
                Log.e("TAG", "getRestaurants: saveFetchResult", )
            }
        },
        //pass in the logic to determine if the networking call should be made
        shouldFetch = { it->
            it.isEmpty()
        }

    )









}