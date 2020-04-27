package com.example.myapplication.application

import android.app.Application
import com.example.myapplication.dagger.DaggerAppComponent

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build()
    }
}