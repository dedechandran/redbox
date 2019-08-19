package com.example.redboxapps.di

import com.example.redboxapps.ui.backgroundtask.GeofenceBroadcastReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ReceiverModule {
    @ContributesAndroidInjector
    abstract fun provideGeofenceBroadcastReceiver() : GeofenceBroadcastReceiver
}