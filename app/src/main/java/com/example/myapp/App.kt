package com.example.myapp

import android.app.Application
import android.content.Context
import android.content.ContextParams
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class App : Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    override fun createContext(contextParams: ContextParams): Context {
        return super.createContext(contextParams)
    }
    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }

    companion object {
        lateinit var instance: App
            private set
    }


}