package com.example.myapplication.dagger

import com.example.myapplication.application.MyApplication
import com.example.myapplication.fragment.GameOnFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivitiesModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder

        fun build(): AppComponent
    }

    fun inject(myApplication: MyApplication)

    fun inject(gameOnFragment: GameOnFragment)
}