package com.example.redboxapps.di

import android.app.Application
import android.content.Context
import com.example.redboxapps.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideAppContext(app: Application): Context = app

    @Provides
    fun provideGeofenceClient(appContext: Context): GeofencingClient = LocationServices.getGeofencingClient(appContext)

    @Provides
    fun provideGoogleSignInOptions(appContext: Context) : GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(appContext.getString(R.string.client_id))
        .requestEmail()
        .build()

    @Provides
    fun provideGoogleSignInClient(appContext: Context,googleSignInOptions: GoogleSignInOptions) : GoogleSignInClient
            = GoogleSignIn.getClient(appContext,googleSignInOptions)

    @Provides
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()
}