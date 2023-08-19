package com.example.myapp.source.remote

import com.example.myapp.source.remote.response.UserListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiEndPoint {

    @GET("/api/users?page=2")
    suspend fun getUserList(/*@Body userRequest: UserRequest*/) : UserListResponse

}