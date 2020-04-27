package com.example.myapplication.dagger

import android.content.Context
import com.example.myapplication.application.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivitiesModule {

    @Provides
    @Singleton
    fun provideApplication(app : MyApplication):Context = app
}