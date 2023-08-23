package com.example.myapp.source.local.room.model

data class GetUserResponse(
    val `data`: Data
) {
    data class Data(
        val audio: String,
        val gif: String,
        val image: String,
        val video: String
    )
}