package com.example.myapp.source.local.room.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapp.source.local.room.Dao.RestaurantDao
import com.example.myapp.source.local.room.model.Restaurant
import com.example.myapp.source.local.room.model.UserModel

@Database(entities = [UserModel.Data::class], version = 2,exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract  fun restaurantDao(): RestaurantDao
}