package com.example.myapp.source.remote

import com.example.myapp.source.local.room.model.Restaurant
import com.example.myapp.source.remote.response.UserListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiEndPoint {

    @GET("/api/restaurant/random_restaurant?size=20")
    suspend fun getUserList(/*@Body userRequest: UserRequest*/) : List<Restaurant>

}