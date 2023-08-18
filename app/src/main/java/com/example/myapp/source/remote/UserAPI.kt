package com.example.myapp.source.remote

import com.example.myapp.source.remote.response.UserListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST("/users/signup")
    suspend fun signup(/*@Body userRequest: UserRequest*/) : Response<UserListResponse>

}