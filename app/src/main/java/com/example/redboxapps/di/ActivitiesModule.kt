package com.example.redboxapps.di

import com.example.redboxapps.ui.MainActivity
import com.example.redboxapps.ui.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector(modules = [(MainModule::class)])
    abstract fun provideMainActivity() : MainActivity
}