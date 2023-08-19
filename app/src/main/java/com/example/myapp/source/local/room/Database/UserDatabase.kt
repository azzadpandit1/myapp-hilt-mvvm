package com.example.myapp.source.local.room.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapp.source.local.room.Dao.UserDao
import com.example.myapp.source.local.room.model.UserModel

@Database(entities = [UserModel.Data::class], version = 2,exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract  fun userDao(): UserDao
}