package com.example.redboxapps.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideFirestore() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideFCM() : FirebaseMessaging = FirebaseMessaging.getInstance()

    @Provides
    fun provideInstanceId() : FirebaseInstanceId = FirebaseInstanceId.getInstance()
}