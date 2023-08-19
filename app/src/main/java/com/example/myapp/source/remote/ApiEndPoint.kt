package com.example.myapp.source.remote

import com.example.myapp.source.local.room.model.Restaurant
import com.example.myapp.source.local.room.model.UserModel
import com.example.myapp.source.remote.response.UserListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiEndPoint {

//    https://reqres.in/api/users?page=2
//    @GET("/api/restaurant/random_restaurant?size=20")
    @GET("/api/users?page=2")
    suspend fun getUserList(/*@Body userRequest: UserRequest*/) : UserModel

}