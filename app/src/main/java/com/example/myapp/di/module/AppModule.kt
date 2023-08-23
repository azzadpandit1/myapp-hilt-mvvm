package com.example.myapp.di.module

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.myapp.App
import com.example.myapp.source.local.room.Database.UserDatabase
import com.example.myapp.source.remote.AuthInterceptor
import com.example.myapp.source.remote.ApiEndPoint
import com.example.myapp.utils.Constants
import com.example.myapp.utils.isNetworkAvailable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule(){

    @Provides
    @Singleton
    fun provideDatabase(app: Application) : UserDatabase =
        Room.databaseBuilder(app, UserDatabase::class.java, "restaurant_database")
            .fallbackToDestructiveMigration()
            .build()


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



    private val cacheSize: Long = 10 * 1024 * 1024  //  10 MiB

    private val cache by lazy {
        Cache(App.instance.cacheDir, cacheSize)
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalResponse: Response = chain.proceed(chain.request())
                    val cacheControl = originalResponse.header("Cache-Control")
                    return if (cacheControl == null ||
                        cacheControl.contains("no-store") ||
                        cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") ||
                        cacheControl.contains("max-age=0")
                    )
                        originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + 5000)
                            .build()
                    else
                        originalResponse
                }
            })
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request: Request = chain.request()
                    if (!isNetworkAvailable(App.instance.applicationContext))
                        request = request.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached")
                            .build()
                    return chain.proceed(request)
                }
            })

            .addInterceptor(logInterceptor.apply {
                /*level = if (NLog.DEBUG_BOOL)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE*/
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val logInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.e("TAG", "log: $message", )
                }
            }
        )
    }


    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }



}