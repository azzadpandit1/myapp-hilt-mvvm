package com.example.myapp.source.local.room.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapp.source.local.room.Dao.RestaurantDao
import com.example.myapp.source.local.room.model.Restaurant
import com.example.myapp.source.remote.response.UserListResponse

@Database(entities = [Restaurant::class], version = 1,exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract  fun restaurantDao(): RestaurantDao
}