package com.example.myapp.source.remote

import com.example.myapp.source.local.room.model.GetUserResponse
import com.example.myapp.source.local.room.model.UserModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndPoint {
    @GET("/azzadpandit1/static-json1/main/README.md")
    suspend fun getUserList(): GetUserResponse




}