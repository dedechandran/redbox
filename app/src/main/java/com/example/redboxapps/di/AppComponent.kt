package com.example.redboxapps.di

import android.app.Application
import com.example.redboxapps.AppCore
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule


@Component(
    modules = [(AndroidInjectionModule::class)
        , (AppModule::class)
        , (ActivitiesModule::class)
        , (ReceiverModule::class)
        , (DataModule::class)]
)
interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app : Application) : Builder
        fun build() : AppComponent
    }

    fun inject(app : AppCore)
}