package com.example.myapp.di.module

import android.app.Application
import androidx.room.Room
import com.example.myapp.source.local.room.Database.RestaurantDatabase
import com.example.myapp.source.remote.AuthInterceptor
import com.example.myapp.source.remote.ApiEndPoint
import com.example.myapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    //database instance
    @Provides
    @Singleton
    fun provideDatabase(app: Application) : RestaurantDatabase =
        Room.databaseBuilder(app, RestaurantDatabase::class.java, "restaurant_database")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): ApiEndPoint {
        return retrofitBuilder.build().create(ApiEndPoint::class.java)
    }



}