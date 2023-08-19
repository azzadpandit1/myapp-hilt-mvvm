package com.example.myapp.source.local.room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapp.source.local.room.model.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM restaurants")
    fun getAllRestaurants(): Flow<List<UserModel.Data>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(restaurants: List<UserModel.Data>)

    @Query("DELETE FROM restaurants")
    suspend fun deleteAllRestaurants()



}